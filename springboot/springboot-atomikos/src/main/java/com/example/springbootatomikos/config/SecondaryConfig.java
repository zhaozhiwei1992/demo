package com.example.springbootatomikos.config;

import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: SecondaryConfig
 * @Package com/example/springbootatomikos/config/SecondaryConfig.java
 * @Description:
 * 2. 分别配置主数据源和其它数据源 * @author zhaozhiwei
 * @date 2022/4/25 上午10:19
 * @version V1.0
 */
@Configuration
@DependsOn("transactionManager")
@EnableJpaRepositories(basePackages = "com.example.springbootatomikos.repository.secondary",   //注意这里
        entityManagerFactoryRef = "secondaryEntityManager",
        transactionManagerRef = "transactionManager")
public class SecondaryConfig {
    @Autowired
    private JpaVendorAdapter jpaVendorAdapter;


    @Bean(name = "secondaryDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSourceProperties masterDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean(name = "secondaryDataSource", initMethod = "init", destroyMethod = "close")
    @ConfigurationProperties(prefix = "spring.datasource.secondary")
    public DataSource masterDataSource() throws SQLException {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(masterDataSourceProperties().getUrl());
        mysqlXaDataSource.setPinGlobalTxToPhysicalConnection(true);
        mysqlXaDataSource.setPassword(masterDataSourceProperties().getPassword());
        mysqlXaDataSource.setUser(masterDataSourceProperties().getUsername());
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("secondary");
        xaDataSource.setBorrowConnectionTimeout(60);
        xaDataSource.setMaxPoolSize(20);
        return xaDataSource;
    }

    @Bean(name = "secondaryEntityManager")
    @DependsOn("transactionManager")
    public LocalContainerEntityManagerFactoryBean masterEntityManager() throws Throwable {

        HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.transaction.jta.platform", AtomikosJtaPlatform.class.getName());
        properties.put("javax.persistence.transactionType", "JTA");
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJtaDataSource(masterDataSource());
        entityManager.setJpaVendorAdapter(jpaVendorAdapter);
        //这里要修改成主数据源的扫描包
        entityManager.setPackagesToScan("com.example.springbootatomikos.domain");
        entityManager.setPersistenceUnitName("secondaryPersistenceUnit");
        entityManager.setJpaPropertyMap(properties);
        return entityManager;
    }
}