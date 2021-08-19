package com.example.springbootdruid.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.example.springbootdruid.filter.DispatchPartitionFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: MultiDataSourceRegister
 * @Package com/example/springbootdruid/config/MultiDataSourceRegister.java
 * @Description: 多数据源支持, 自定义方式注入到spring容器中, 使用时根据key动态使用
 * {@see org.springframework.boot.jdbc.DataSourceBuilder}
 * @author zhaozhiwei
 * @date 2021/7/5 下午9:35
 * @version V1.0
 */
public class MultiDataSourceRegister implements EnvironmentAware, ImportBeanDefinitionRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(MultiDataSourceRegister.class);

    //别名
    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();

    static {
        //由于部分数据源配置不同，所以在此处添加别名，避免切换数据源出现某些参数无法注入的情况
        aliases.addAliases("url", new String[]{"jdbc-url"});
        aliases.addAliases("username", new String[]{"user"});
    }

    /**
     * 配置上下文（也可以理解为配置文件的获取工具）
     */
    private Environment evn;

    /**
     * 数据源列表
     */
    private Map<String, DataSource> customDataSources;

    /**
     * 参数绑定工具
     */
    private Binder binder;

    private boolean isSuportJTA() {
        boolean suportJta  = binder.bind("spring.datasource.jta", Boolean.class).get();
        return  suportJta;
    }

    /**
     * ImportBeanDefinitionRegistrar接口的实现方法，通过该方法可以按照自己的方式注册bean
     *
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        logger.info("注册默认数据源开始");
        if (!binder.bind("spring.datasource", Map.class).isBound()) {
            return;
        }

        //获取所有数据源配置
        Map config, properties, defaultConfig, atomikosConfig = null;
        defaultConfig = binder.bind("spring.datasource", Map.class).get();
        defaultConfig.put("type", "com.alibaba.druid.pool.DruidDataSource");
        customDataSources = new HashMap<>();
        //默认配置
        properties = defaultConfig;
        //默认数据源类型
        String typeStr = (String) properties.get("type");
        String defaultTargetKey = "default";
        //获取数据源类型
        Class<? extends DataSource> clazz = getDataSourceType(typeStr);
        //绑定默认数据源参数
        DataSource consumerDatasource, defaultDatasource = bind(clazz, properties);
        addFilters(defaultDatasource);
        boolean openMulti  = binder.bind("spring.datasource.openmulti", Boolean.class).get();
        logger.info("注册默认数据源{}成功",defaultTargetKey);
        if(openMulti && binder.bind("spring.datasource.multi", Map.class).isBound()) {
            //获取其他数据源配置
            List<Map> configs = binder.bind("spring.datasource.multi", Bindable.listOf(Map.class)).get();
            //遍历生成其他数据源
            for (Map map : configs) {
                config = map;
                //获取extend字段，未定义或为true则为继承状态
                if ((boolean) config.getOrDefault("extend", Boolean.TRUE)) {
                    //继承默认数据源配置
                    properties = new HashMap(defaultConfig);
                    //添加数据源参数
                    properties.putAll(config);
                } else {
                    //不继承默认配置
                    properties = config;
                }
                //绑定参数

                if (properties.containsKey("type")) {
                    Class<? extends DataSource> clazz1 = getDataSourceType((String) properties.get("type"));
                    consumerDatasource = bind(clazz1, properties);
                } else {
                    consumerDatasource = bind(clazz, properties);
                }
                // 获取数据源的key，以便通过该key可以定位到数据源
                String key = properties.get("key").toString();
                //获取数据源的key，以便通过该key可以定位到数据源
                addFilters(consumerDatasource);
                customDataSources.put(key, consumerDatasource);
                logger.info("注册数据源{}成功", key);

            }
        }
        //bean定义类
        GenericBeanDefinition define = new GenericBeanDefinition();
        //设置bean的类型，此处MultiDataSource是继承AbstractRoutingDataSource的实现类
        define.setBeanClass(MultiDataSource.class);
        //需要注入的参数，类似spring配置文件中的<property/>
        MutablePropertyValues mpv = define.getPropertyValues();
        //添加默认数据源，避免key不存在的情况没有数据源可用
        mpv.add("defaultTargetDataSource", defaultDatasource);
        //添加其他数据源
        mpv.add("targetDataSources", customDataSources);
        //将该bean注册为datasource，不使用springboot自动生成的datasource
        beanDefinitionRegistry.registerBeanDefinition("datasource", define);
//        BeanDefinitionRegistry registry = ((BeanDefinitionRegistry) ((DefaultListableBeanFactory) beanDefinitionRegistry).getParentBeanFactory());
//        registry.registerBeanDefinition("datasource", define);
        logger.info("注册数据源成功，一共注册{}个数据源", customDataSources.keySet().size() + 1);
    }

    /**
     * 通过字符串获取数据源class对象
     *
     * @param typeStr
     * @return
     */
    private Class<? extends DataSource> getDataSourceType(String typeStr) {
        Class<? extends DataSource> type;
        try {
            //字符串不为空则通过反射获取class对象
            if (StringUtils.hasLength(typeStr)) {
                type = (Class<? extends DataSource>) Class.forName(typeStr);
            } else {
                //默认为hikariCP数据源，与springboot默认数据源保持一致
                type = DruidDataSource.class;
            }
            return type;
        } catch (Exception e) {
            //无法通过反射获取class对象的情况则抛出异常，该情况一般是写错了，所以此次抛出一个runtimeexception
            throw new IllegalArgumentException("can not resolve class with type: " + typeStr);
        }
    }

    /**
     * 绑定参数，以下三个方法都是参考DataSourceBuilder的bind方法实现的，目的是尽量保证我们自己添加的数据源构造过程与springboot保持一致
     *
     * @param result
     * @param properties
     */
    private void bind(DataSource result, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        //将参数绑定到对象
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
    }

    private <T extends DataSource> T bind(Class<T> clazz, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        //通过类型绑定参数并获得实例对象
        return binder.bind(ConfigurationPropertyName.EMPTY, Bindable.of(clazz)).get();
    }

    /**
     * @param clazz
     * @param sourcePath 参数路径，对应配置文件中的值，如: spring.datasource
     * @param <T>
     * @return
     */
    private <T extends DataSource> T bind(Class<T> clazz, String sourcePath) {
        Map properties = binder.bind(sourcePath, Map.class).get();
        return bind(clazz, properties);
    }

    private void addFilters(DataSource dataSource) {
        if (dataSource instanceof DruidDataSource) {
            DruidDataSource druidDataSource = (DruidDataSource) dataSource;
            List<Filter> filters = new ArrayList<Filter>();
//            if(DbTypeUtil.DB_TYPE_POLARDB.equals(DbTypeUtil.getPhysicsDbType())) {
//            增加数据源过滤器，处理特殊业务
                filters.add(new DispatchPartitionFilter());
//            }
            druidDataSource.setProxyFilters(filters);
            try {
                druidDataSource.addFilters("config");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * EnvironmentAware接口的实现方法，通过aware的方式注入，此处是environment对象
     *
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.evn = environment;
        //绑定配置器
        binder = Binder.get(evn);
    }

}
