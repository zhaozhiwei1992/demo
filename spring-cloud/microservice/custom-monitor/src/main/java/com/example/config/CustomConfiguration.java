package com.example.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * 一些通用配置，按需引用
 */
public class CustomConfiguration {

    @Bean
    public FilterRegistrationBean userSyncFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean(new BusCommonPrefixFilter());
//        registration.addUrlPatterns("*");
//        registration.setName("BusCommonPrefixFilter");
//        registration.setOrder(1);
//        return registration;
        return null;
    }
}
