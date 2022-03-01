package com.example.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: TODO
 *
 * 使用容器管理类型的JPA
 * 1. 使用LocalContainerEntity-ManagerFactoryBean来配置容器管理类型的JPA
 * 2. 使用Hibernate作为JPA实现，所以将其配置为Hibernate-JpaVendorAdapter
 * @date 2022/3/1 上午9:10
 */
@Configuration
public class JpaEntityManagerConfig {

    //1. datasource 一个地方实现了就行 com.example.config.HibernateSessionConfig.dataSource

    //2. LocalContainerEntityManagerFactoryBean
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("com.example.domain");
        return entityManagerFactoryBean;
    }

    /**
     * @data: 2022/3/1-上午9:15
     * @User: zhaozhiwei
     * @method: jpaVendorAdapter

     * @return: org.springframework.orm.jpa.JpaVendorAdapter
     * @Description: 描述
     * Spring提供了多个JPA厂商适配器：
     * EclipseLinkJpaVendorAdapter
     * HibernateJpaVendorAdapter
     * OpenJpaVendorAdapter
     * TopLinkJpaVendorAdapter（在Spring 3.1版本中，已经将其
     * 废弃了）
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){

        final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.H2);
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL55Dialect");
        return hibernateJpaVendorAdapter;
    }

    /**
     * @Description:
     * {@see com.example.config.HibernateSessionConfig#transactionManager(org.hibernate.SessionFactory)}
     * 两个事务管理器不能同时存在, 否则测试时只有一个生效, 容易误导
     */
    @Bean
    public JpaTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }

}
