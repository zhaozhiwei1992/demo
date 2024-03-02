package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 用户服务提供者: 用来对外提供用户服务, 类似平时写的一些service
 * 需要注册到eureka中心
 *
 * @author zhaoz
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserProviderMain
        implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(UserProviderMain.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//      拦截请求
//        registry.addInterceptor(new CustomIntercepter());
    }
}
