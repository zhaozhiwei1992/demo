package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: TODO
 * @date 2022/2/19 下午11:02
 */
@Configuration
@ComponentScan(basePackages = "com.example")
//通过javaconfig配置aop
//@Import(value = {AopConfig.class})
// 通过xml方式配置aop
@ImportResource(locations = "classpath:spring-context.xml")
public class SystemConfig {

}
