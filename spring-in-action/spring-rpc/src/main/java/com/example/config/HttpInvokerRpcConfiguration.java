package com.example.config;

import com.example.service.UserService;
import com.example.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

//@Configuration
public class HttpInvokerRpcConfiguration {

    @Bean("/user.http")
    public HttpInvokerServiceExporter userServiceExporter(){
        final HttpInvokerServiceExporter burlapServiceExporter = new HttpInvokerServiceExporter();
        burlapServiceExporter.setService(new UserServiceImpl());
        burlapServiceExporter.setServiceInterface(UserService.class);
        return burlapServiceExporter;
    }

    @Bean
    public HttpInvokerProxyFactoryBean httpClient(){
        final HttpInvokerProxyFactoryBean hessianProxyFactoryBean = new HttpInvokerProxyFactoryBean();
        hessianProxyFactoryBean.setServiceUrl("http://localhost:8080/user.http");
        hessianProxyFactoryBean.setServiceInterface(UserService.class);
        return hessianProxyFactoryBean;
    }

    @Bean
    public BeanNameUrlHandlerMapping nameUrlHandlerMapping(){
        return new BeanNameUrlHandlerMapping();
    }
}
