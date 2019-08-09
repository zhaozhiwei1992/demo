package com.lx.demo.config;

import com.lx.demo.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置多数据源
 */
@Configuration
@MapperScan(basePackages = "com.lx.demo.mapper.dynamic", sqlSessionTemplateRef = "dynamicSqlSessionTemplate")
public class DataSourceConfigDynamic {


    /**
     * 创建主datasource, 这里多个数据源时必须指定一个为primary
     * @return
     */
    @ConfigurationProperties("spring.datasource.master")
    @Bean
    @Primary
    public DataSource dataSource1(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建从datasource, 这里多个数据源时必须指定一个为primary
     * @return
     */
    @ConfigurationProperties("spring.datasource.slaver")
    @Bean
    public DataSource dataSource2(){
        return DataSourceBuilder.create().build();
    }


    /**
     * 动态数据源: 通过AOP在不同数据源之间动态切换
     * @return
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource1());

        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap(5);
        dsMap.put("master", dataSource1());
        dsMap.put("slaver", dataSource2());
        dynamicDataSource.setTargetDataSources(dsMap);

        return dynamicDataSource;
    }

    /**
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "dynamicSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //如果使用xml的方式, 需要设置mybatis配置文件路径
//        sqlSessionFactoryBean.setConfigLocation("");
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 主域 事务管理器
     * @param dataSource
     * @return
     */
    @Bean(name = "dynamicDataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dynamicDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 主 sqlsessiontemplet
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name = "dynamicSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("dynamicSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
