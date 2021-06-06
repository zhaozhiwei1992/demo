package com.lx.demo.springbootsecurity.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.springbootsecurity.configuration
 * @Description: 懒得写controller的部分
 * @date 2021/6/6 下午5:56
 */
@Configuration
public class CustomWebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/access").setViewName("hello");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/signOut").setViewName("signOut");
    }
}
