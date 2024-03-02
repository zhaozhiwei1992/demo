package com.example.springbootdruid.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@Import({MultiDataSourceRegister.class})
@ConditionalOnProperty(prefix = "spring.datasource", name = "url")
public class DataSourceConfig {

    /**
     * 创建由动态数据源提供的JdbcTemplete
     *
     * 手动注册datasource: beanDefinitionRegistry.registerBeanDefinition("datasource", define);
     * druidDatasource: com.example.springbootdruid.config.DruidConfiguration#druid()
     * @param dataSource
     * @return
     */
    @Bean(name = "JdbcTemplate")
    @Primary
    public JdbcTemplate primaryJdbcTemplate(
            @Qualifier("druid") DataSource dataSource) {
//        @Qualifier("datasource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
