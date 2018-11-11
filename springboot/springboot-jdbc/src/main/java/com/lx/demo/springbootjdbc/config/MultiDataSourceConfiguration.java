package com.lx.demo.springbootjdbc.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 多数据源配置
 */
@Configuration
public class MultiDataSourceConfiguration {

    /**
     * 主数据源
     *
     * 多个数据源配置时必须有一个默认的，打标记为primary
     * @return
     */
    @Bean
    @Primary
    public DataSource masterDataSource(){
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/test1")
                .username("root")
                .password("root").build();
    }

    /**
     * 从
     * @return
     */
    @Bean
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/test2")
                .username("root")
                .password("root")
                .build();
    }
}
