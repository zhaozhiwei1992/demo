package com.example.springbootdruid.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Title: MultiDataSource
 * @Package com/example/springbootdruid/config/MultiDataSource.java
 * @Description: 利用threadlocal和spring jdbc能力，动态切换数据源
 * {@see com.lx.demo.datasourcerouting.MasterSlaverDataSource}
 * @author zhaozhiwei
 * @date 2021/7/5 下午9:43
 * @version V1.0
 */
public class MultiDataSource extends AbstractRoutingDataSource {

    private static Logger logger = LoggerFactory.getLogger(MultiDataSource.class);


    /**
     * 保存当前线程的数据源对应的key
     */
    private final static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
    * 所有数据源的key集合
    */
    private final static  Set<Object> keySet = new LinkedHashSet<>();

    public static void setKey(String key) {
        logger.debug("切换至{}数据源", key);
        //切换当先线程的key
        contextHolder.set(key);
    }

    public static void clearKey() {
        if(contextHolder.get() !=null) {
            logger.debug("移除{}数据源", contextHolder.get());
            //移除key值
            contextHolder.remove();
        }
    }


    /**
     * 判断指定DataSrouce当前是否存在
     *  @param key
     *  @return
     */
    public static boolean containsKey(String key) {
        return keySet.contains(key);
    }

    public static void addDatasource(DataSource dataSource, String key,  DataSource parse) {
        if(dataSource instanceof AbstractRoutingDataSource){
            try {
                Field sourceMapField = AbstractRoutingDataSource.class.getDeclaredField("resolvedDataSources");
                sourceMapField.setAccessible(true);
                Map<Object, DataSource> sourceMap = (Map<Object, DataSource>) sourceMapField.get(dataSource);
                sourceMap.put(key,parse);
                keySet.add(key);
                sourceMapField.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public DataSource getDefaultDataSource(){
        return super.determineTargetDataSource();
    }


    /**
     * AbstractRoutingDataSource抽象类实现方法，即获取当前线程数据源的key
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String key = contextHolder.get();
        if (!keySet.contains(key)) {
            logger.debug(String.format("can not found datasource by key: '%s',this session may use default datasource", key));
        }
        if(key == null){
            logger.debug("当前数据源是：{}", "默认数据源");
        } else {
            logger.debug("当前数据源是：{}", key);
        }
        return key;
    }

    /**
     * 在获取key的集合，目的只是为了添加一些告警日志
     */
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        try {
            Field sourceMapField = AbstractRoutingDataSource.class.getDeclaredField("resolvedDataSources");
            sourceMapField.setAccessible(true);
            Map<Object, DataSource> sourceMap = (Map<Object, DataSource>) sourceMapField.get(this);
            keySet.addAll(sourceMap.keySet());
            sourceMapField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
