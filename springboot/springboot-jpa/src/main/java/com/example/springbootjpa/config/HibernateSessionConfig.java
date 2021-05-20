package com.example.springbootjpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Title: HibernateSessionConfig
 * @Package com/example/springbootjpa/config/HibernateSessionConfig.java
 * @Description: 目前没找到sessionfactory和 EntityManagerFactory共存方式
 * @author zhaozhiwei
 * @date 2021/5/20 下午10:40
 * @version V1.0
 */
//@Configuration
public class HibernateSessionConfig {

    //第一个数据源
    @Bean(name = "firstDataSource")
    @ConfigurationProperties(prefix = "app.datasource.first")//application.properties文件中前缀配置引用
    @Primary//多个数据源时首先注入
    public DataSource firstDataSource() {
        return DataSourceBuilder.create().build();
    }
    //第二个数据源
    @Bean(name="secondDataSource")
    @ConfigurationProperties(prefix="app.datasource.second")//application.properties文件中前缀配置引用
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }

    //注入第一个数据源，生成sessionFactory
    @Autowired
    @Bean("sessionFactory")
    @Primary
    public LocalSessionFactoryBean getSessionFactory(@Qualifier("firstDataSource")DataSource dataSource) {
        return buildLocalSessionFactory(dataSource);
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
        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
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
        localSessionFactoryBean.setPackagesToScan("com.ml.hibernatepro.ml.domain");
        return localSessionFactoryBean;
    }
    //注入第二个数据源生成secondSessionFactory
    @Autowired
    @Bean("secondSessionFactory")
    public LocalSessionFactoryBean getSecondSessionFactory(@Qualifier("secondDataSource")DataSource dataSource) {
        return buildLocalSessionFactory(dataSource);
    }
}