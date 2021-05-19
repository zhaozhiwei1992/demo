package com.example.springbootpolardb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 多数据源配置
 * {@see org.springframework.context.annotation.Configuration}
 */
@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfiguration {

    @Value("${spring.datasource.driverClassName}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 主数据源
     * 这里的数据源会冲掉在applicatioin.yaml中配置的数据源，primary才是大爷
     *
     * 多个数据源配置时必须有一个默认的，打标记为primary
     * @return
     * build 时候会使用spring默认datasource类  {@see com.zaxxer.hikari.HikariDataSource}
     * {@see com.zaxxer.hikari.HikariConfig}
     */
    @Bean
    @Primary
    public DataSource masterDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(username)
                .password(password).build();
    }
}
