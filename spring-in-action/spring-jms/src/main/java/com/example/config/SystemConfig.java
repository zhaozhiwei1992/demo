package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description:
 *
 * 默认情况下，@PropertySource不会加载YAML文件。
 * @date 2022/2/28 下午2:38
 */
@Configuration
@ComponentScan(basePackages = "com.example")
//@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertyLoaderFactory.class, encoding = "UTF-8")
public class SystemConfig {


}
