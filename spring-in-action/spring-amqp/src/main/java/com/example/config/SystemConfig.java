package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "com.example")
@PropertySource(value = "classpath:application.yaml", factory = YamlPropertyLoaderFactory.class, encoding = "UTF-8")
public class SystemConfig {

}
