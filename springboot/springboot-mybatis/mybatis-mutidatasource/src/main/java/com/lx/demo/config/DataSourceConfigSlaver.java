package com.lx.demo.config;

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

/**
 * 从域配置
 *  包括datasource session等, 都搞一套自己的
 */
@Configuration
@MapperScan(basePackages = "com.lx.demo.mapper.slaver", sqlSessionTemplateRef = "slaverSqlSessionTemplate")
public class DataSourceConfigSlaver {

    /**
     * 创建从datasource, 这里多个数据源时必须指定一个为primary
     * @return
     */
    @ConfigurationProperties("spring.datasource.slaver")
    @Bean(name = "slaverDataSource")
    public DataSource dataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "slaverSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("slaverDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //如果使用xml的方式, 需要设置mybatis配置文件路径
//        sqlSessionFactoryBean.setConfigLocation("");
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 从域 事务管理器
     * @param dataSource
     * @return
     */
    @Bean(name = "slaverDataSourceTransactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("slaverDataSource") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 从 sqlsessiontemplet
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name = "slaverSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("slaverSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
