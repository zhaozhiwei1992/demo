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
@ComponentScan(basePackages = "com.example",
        excludeFilters = {
//        去掉webmvc相关配置
            @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
        }
)
//@ImportResource(locations = "classpath:spring-context.xml")
public class SystemConfig {
}
