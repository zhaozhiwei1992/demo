package com.example.springbootjwt.config;

import com.example.springbootjwt.intercept.LoginIntercepter;
import com.example.springbootjwt.intercept.TokenIntercepter;
import com.example.springbootjwt.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DomainWebConfiger implements WebMvcConfigurer {

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
//        registry.addInterceptor(new TokenIntercepter(tokenProvider)).addPathPatterns("/api/index");
        registry.addInterceptor(new LoginIntercepter()).addPathPatterns("/**").excludePathPatterns("/", "/login", "/index", "/api/login", "/api/index");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/redir/login").setViewName("login2");
    }

}
