package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: TODO
 * @date 2022/3/1 下午2:49
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.example.repository")
public class JpaConfiguration {

}
