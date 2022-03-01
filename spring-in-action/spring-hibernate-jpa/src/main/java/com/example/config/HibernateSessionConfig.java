package com.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.hibernate.SessionFactory;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Title: HibernateSessionConfig
 * @Package com/example/config/HibernateSessionConfig.java
 * @Description:
 *
 * 参考: <<spring in action 4>>
 * 最佳实践是不再使用HibernateTemplate，而是使用上下
 * 文Session（Contextual session）。通过这种方式，会直接将Hibernate
 * SessionFactory装配到Repository中，并使用它来获取Session
 *
 * @author zhaozhiwei
 * @date 2022/2/28 上午11:18
 * @version V1.0
 */
@Configuration
public class HibernateSessionConfig {

    @Bean
    public DataSource dataSource() {
//        return DataSourceBuilder.create().build();
        final DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("org.h2.Driver");
//        hiber ddl-auto 设置使用了mysql dialect需要匹配,否则语法不生效
        druidDataSource.setUrl("jdbc:h2:~/test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");

        druidDataSource.setInitialSize(5);
        druidDataSource.setMinIdle(5);
        druidDataSource.setMaxActive(300);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(true);

//		<!-- 配置获取连接等待超时的时间 -->
        druidDataSource.setMaxWait(5000);
//		<!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
        druidDataSource.setMinEvictableIdleTimeMillis(30000);
//		<!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
//		<!-- 解密密码必须要配置的项 -->
//        druidDataSource.setFilters("config");
//        druidDataSource.setConnectProperties("");

        return druidDataSource;
    }

    @Bean
    public LocalSessionFactoryBean localSessionFactory(DataSource dataSource) {
        return buildLocalSessionFactory(dataSource);
    }

    /**
     * @data: 2022/2/28-下午3:54
     * @User: zhaozhiwei
     * @method: transactionManager
      * @param sessionFactory :
     * @return: org.springframework.orm.hibernate5.HibernateTransactionManager
     * @Description: 描述
     * org.hibernate.HibernateException: Could not obtain transaction-synchronized Session for current thread
     * 需要初始化下述bean, 并且如果bean id为 transactionManager, 实现类直接使用@Transactional,
     */
//    @Bean
    public HibernateTransactionManager transactionManager(@Qualifier("localSessionFactory") SessionFactory sessionFactory){
        return new HibernateTransactionManager(sessionFactory);
    }

    /**
     * @data: 2022/2/28-下午4:15
     * @User: zhaozhiwei
     * @method:
     * @param platformTransactionManager spring提供的事务管理器父类，自行实现, 这里实际为HibernateTransactionManager
     * @return:
     * @Description: 描述
     * 等价xml中指定tx: <tx:annotation-driven transaction-manager="transactionManager"/>
     * https://stackoverflow.com/questions/48450504/spring-boot-aop-tx-advice-in-java-config-without-xml-config
     */
    @Bean(name = "transactionInterceptor")
    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager platformTransactionManager) {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(platformTransactionManager);
        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED,-Throwable");
        transactionAttributes.setProperty("tranNew*", "PROPAGATION_REQUIRES_NEW,-Throwable");
        transactionInterceptor.setTransactionAttributes(transactionAttributes);
        return transactionInterceptor;
    }

    @Bean
    public BeanNameAutoProxyCreator transactionAutoProxy() {
        BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
        transactionAutoProxy.setProxyTargetClass(true);
//        一般指定到Service级别, 约定名称末尾为Service
        transactionAutoProxy.setBeanNames("*Service");
        transactionAutoProxy.setInterceptorNames("transactionInterceptor");
        return transactionAutoProxy;
    }

    /**
     * @Description: 描述
     * 为了给不使用模板的Hibernate Repository添加异常转换功能，我们只
     * 需在Spring应用上下文中添加一
     * 个PersistenceExceptionTranslationPostProcessorbean
     *
     * PersistenceExceptionTranslationPostProcessor是一个bean
     * 后置处理器（bean post-processor），它会在所有拥
     * 有@Repository注解的类上添加一个通知器（advisor），这样就会
     * 捕获任何平台相关的异常并以Spring非检查型数据访问异常的形式重
     * 新抛出
     */
    @Bean
    public BeanPostProcessor persistenceTransaction(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    /**
     * 设置Hibernate的配置属性
     * @return
     */
    private Properties getHibernateProperties(){
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect","org.hibernate.dialect.MySQL55Dialect");
        hibernateProperties.put("current_session_context_class", "org.springframework.orm.hibernate5.SpringSessionContext");
        hibernateProperties.put("hibernate.show_sql", "true");
        hibernateProperties.put("hibernate.format_sql", "false");
//        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
//        https://stackoverflow.com/questions/38568482/spring-hibernate-h2-junit-testing-how-to-load-schema-on-start
        hibernateProperties.put("hibernate.hbm2ddl.auto", "create");
        return hibernateProperties;
    }

    /**
     * 构建LocalSessionFactoryBean实例
     * @param dataSource 构建实例所使用的的数据源
     * @return
     */
    private LocalSessionFactoryBean buildLocalSessionFactory(DataSource dataSource){
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource); // 配置数据源,指定成第一个数据源
        // 如果使用 xml 配置则使用该方法进行包扫描
        //PathMatchingResourcePatternResolver pmprpr = new PathMatchingResourcePatternResolver();
        //Resource[] resource = pmprpr.getResources("classpath*:com/ml/hibernatepro/ml/domain/*.hbm.xml");
        //localSessionFactoryBean.setMappingLocations(resource);

        // 现在配置基本都切换到 java config
        //localSessionFactoryBean.setAnnotatedPackages("classpath*:com/ml/hibernatepro/ml/domain");
        // 添加 Hibernate 配置规则
        localSessionFactoryBean.setHibernateProperties(getHibernateProperties());
        //指定需要扫描的hibernate的Entity实体类包名，可以指定多个包名
        localSessionFactoryBean.setPackagesToScan("com.example.domain");
        return localSessionFactoryBean;
    }
}