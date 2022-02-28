package com.example.config;

import org.springframework.context.annotation.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: TODO
 * @date 2022/2/28 下午2:38
 */
@Configuration
@ComponentScan(basePackages = "com.example")
@PropertySource(value = "classpath:application.yaml", encoding = "UTF-8")
public class SystemConfig {
}
