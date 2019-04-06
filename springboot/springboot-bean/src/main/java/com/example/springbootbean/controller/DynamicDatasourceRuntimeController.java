package com.example.springbootbean.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 通过springjdbc实现 运行时创建bean
 */
@RestController
public class DynamicDatasourceRuntimeController {

    @Autowired
    private ApplicationContext applicationContext;


    /**
     * 增加普通对象
     * @return
     */
    @GetMapping("/addTemplate/{beanName}")
    public String addBean(@PathVariable String beanName) throws SQLException {
        JdbcTemplate jdbcTemplate = newJdbcTemplate(beanName);
        //尝试连接
        try {
            jdbcTemplate.getDataSource().getConnection();
        } catch (SQLException e) {
        }
        System.out.println("连接成功");
        Boolean aBoolean = jdbcTemplate.query("select * from t_user where name = 'admin'", ResultSet::next);
        System.out.println(aBoolean);
        return "";
    }

    @GetMapping("/getTemplate/{beanName}")
    public void beanDefinitions(@PathVariable String beanName){
//        Stream.of(applicationContext.getBeanDefinitionNames()).map(name -> "->" + name).forEach(System.out::println);
        if(applicationContext.containsBean(beanName)){
            System.out.println(applicationContext.getBean(beanName));
        }
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String beanName = "jdbcTemplate";

    /**
     * 每次调用此方法，都会新创建一个JdbcTemplate并注入到Spring上下文中
     * @return
     * @throws SQLException
     */
    public JdbcTemplate newJdbcTemplate(String beanName) throws SQLException {

        //创建一个新的JdbcTemplate
        //测试bean是否注册成功
        DataSource dataSource = DataSourceBuilder.create()
                .driverClassName("com.mysql.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/user")
                .username("root")
                .password("root").build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //判断
        if (applicationContext.containsBeanDefinition(beanName)){
            //如果Spring上下文已存在Bean，先删除
            ((BeanDefinitionRegistry)applicationContext).removeBeanDefinition(beanName);
        }else{
            //如果不存在，创建
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(JdbcTemplate.class);
            ((BeanDefinitionRegistry)applicationContext).registerBeanDefinition(beanName,builder.getBeanDefinition());
        }

        //注入JdbcTemplate实例
        ((ConfigurableApplicationContext)applicationContext).getBeanFactory().registerSingleton(beanName,jdbcTemplate);

        //打印测试：
        logger.info("set dataSource : "+dataSource);
        logger.info("set jdbcTemplate : "+jdbcTemplate);
        logger.info("set jdbcTemplate.dataSource : "+jdbcTemplate.getDataSource());
        return jdbcTemplate;
    }
}
