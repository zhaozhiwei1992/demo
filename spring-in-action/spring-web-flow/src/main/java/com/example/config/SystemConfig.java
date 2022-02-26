package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: 系统配置， 聚合
 * @date 2022/2/21 上午9:13
 */
@Configuration
@ComponentScan(basePackages = "com.example")
//@ImportResource(locations = {
//        "classpath:springmvc-context.xml",
//        "classpath:spring-web-flow.xml"})
public class SystemConfig {
}
