package com.example.springbootseata.config;

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
     * 这里的数据源会冲掉在applicatioin.yaml中配置的数据源，primary才是大爷
     *
     * 多个数据源配置时必须有一个默认的，打标记为primary
     * @return
     */
    @Bean
    @Primary
    public DataSource masterDataSource(){
        return DataSourceBuilder.create()
                .driverClassName("oracle.jdbc.OracleDriver")
                .url("jdbc:oracle:thin:@192.168.100.10:1521/pdb19")
                .username("mid_ox")
                .password("1").build();
    }

    /**
     * 从
     * @return
     */
    @Bean
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create()
                .driverClassName("oracle.jdbc.OracleDriver")
                .url("jdbc:oracle:thin:@192.168.100.10:1521/pdb19")
                .username("mid_ox")
                .password("1")
                .build();
    }
}
