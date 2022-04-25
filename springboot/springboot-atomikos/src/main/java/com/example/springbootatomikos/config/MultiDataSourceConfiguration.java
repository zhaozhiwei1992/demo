package com.example.springbootatomikos.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 1. 多数据源配置
 */
@Configuration
public class MultiDataSourceConfiguration {

    /**
     * 主数据源
     * 这里的数据源会冲掉在applicatioin.yaml中配置的数据源，primary才是大爷
     * <p>
     * 多个数据源配置时必须有一个默认的，打标记为primary
     *
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 从
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource.secondary")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create()
//                .driverClassName("com.mysql.jdbc.Driver")
//                .url("jdbc:mysql://localhost:3306/db2")
//                .username("root")
//                .password("root")
                .build();
    }
}
