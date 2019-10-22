package com.example.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by CrazyMouse on 2017/7/20.
 */
@Configuration
public class AuthFeignConguration {

    @Bean
    public RequestInterceptor authFeignRequestInterceptor() {
        return new AuthFeignRequestInterceptor();
    }

}