package com.example.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: 该类作为聚合配置，整合系统中的各个配置文件, 包括javaconfig和xmlconfig
 * @date 2022/2/16 下午2:57
 */
@Configuration
@Import(value = {AnimalsConfig.class})
@ImportResource("classpath:spring-context.xml")
//ConfigurationClassParser.processPropertySource() 解析
@PropertySource(value = "classpath:app.properties", encoding = "UTF-8")
public class SystemConfig {

}
