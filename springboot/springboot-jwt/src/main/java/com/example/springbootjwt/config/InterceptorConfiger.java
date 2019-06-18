package com.example.springbootjwt.config;

import com.example.springbootjwt.intercept.TokenIntercepter;
import com.example.springbootjwt.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiger implements WebMvcConfigurer {

    @Autowired
    private TokenProvider tokenProvider;

    /**
     *  // 多个拦截器组成一个拦截器链
     *         // addPathPatterns 用于添加拦截规则
     *         // excludePathPatterns 用户排除拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenIntercepter(tokenProvider)).addPathPatterns("/api/index");
    }
}
