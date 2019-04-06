package com.example.springbootbean.controller;

import com.example.springbootbean.domain.MyDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@RestController
public class DynamicBeanController {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 增加普通对象
     * @return
     */
    @GetMapping("/addBean/{beanName}")
    public String addBean(@PathVariable String beanName){

        //参数可变化
        Map properties = new Properties();
        properties.put("driverClassName", "com.example.springbootbean.domain.MyDataSource");
//        properties.put("driverClassName", "com.mysql.jdbc.Driver");
        properties.put("username", "root");
        properties.put("password", "root");
        properties.put("url", "jdbc:mysql://localhost:3306/user");


        //测试bean是否注册成功
//        DataSource dataSource = DataSourceBuilder.create()
//                .driverClassName("com.mysql.jdbc.Driver")
//                .url("jdbc:mysql://localhost:3306/user")
//                .username("root")
//                .password("root").build();
//        properties.put("datasource", dataSource);
        addBean(String.valueOf(properties.get("driverClassName")), beanName, properties, applicationContext);
        return "";
    }

    @GetMapping("/getbean/{beanName}")
    public void beanDefinitions(@PathVariable String beanName){
//        Stream.of(applicationContext.getBeanDefinitionNames()).map(name -> "->" + name).forEach(System.out::println);
        if(applicationContext.containsBean(beanName)){
            System.out.println(applicationContext.getBean(beanName));
        }
    }

    /**
     *  datasource
     *  org.springframework.beans.NotWritablePropertyException: Invalid property 'datasource' of bean class [com.example.springbootbean.domain.MyDataSource]: Bean property 'datasource' is not writable or has an invalid setter method. Did you mean 'dataSource'?
     *  这方式不行
     * @param name
     */
    @GetMapping("/search/{name}")
    public void searchSome(@PathVariable String name){
        MyDataSource myDataSource = (MyDataSource) applicationContext.getBean("goudan");
        DataSource dataSource = myDataSource.getDataSource();
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("select * from t_user where username = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            List list = new ArrayList();
            ResultSetMetaData md = resultSet.getMetaData();//获取键名
            int columnCount = md.getColumnCount();//获取行的数量
            while (resultSet.next()) {
                Map rowData = new HashMap();//声明Map
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), resultSet.getObject(i));//获取键名及值
                }
                list.add(rowData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param className 注册class 全称
     * @param serviceName 注册别名
     * @param propertyMap 注入属性
     * @param app application上下文
     */
    private static void addBean(String className,String serviceName,Map propertyMap, ApplicationContext app){
        try {
            Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            if(propertyMap!=null){
                Iterator<?> entries = propertyMap.entrySet().iterator();
                Map.Entry<?, ?> entry;
                while (entries.hasNext()) {
                    entry = (Map.Entry<?, ?>) entries.next();
                    String key = (String) entry.getKey();
                    Object val =  entry.getValue();
                    beanDefinitionBuilder.addPropertyValue(key, val);
                }
            }
            registerBean(serviceName, beanDefinitionBuilder.getRawBeanDefinition(), app);
        } catch (ClassNotFoundException e) {
            System.out.println(className+",主动注册失败.");
        }
    }

    /**
     * @desc 向spring容器注册bean
     * @param beanName
     * @param beanDefinition
     */
    private static void registerBean(String beanName, BeanDefinition beanDefinition, ApplicationContext context) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) context;
        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
                .getBeanFactory();
        beanDefinitonRegistry.registerBeanDefinition(beanName, beanDefinition);
    }
}
