package com.lx.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
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

/**
 * 主域配置
 *  包括datasource session等, 都搞一套自己的
 */
@Configuration
@MapperScan(basePackages = "com.lx.demo.mapper.master", sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class DataSourceConfigMaster {

    /**
     * 创建主datasource, 这里多个数据源时必须指定一个为primary
     * @return
     */
    @ConfigurationProperties("spring.datasource.master")
    @Bean(name = "masterDataSource")
    @Primary
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource) throws Exception {
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
    @Bean(name = "masterDataSourceTransactionManager")
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("masterDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 主 sqlsessiontemplet
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
