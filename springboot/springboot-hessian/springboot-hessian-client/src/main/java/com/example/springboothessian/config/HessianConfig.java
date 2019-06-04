package com.example.springboothessian.config;

import com.example.springboothessian.web.hessian.SimpleHessian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
public class HessianConfig {

    // 客户端
    @Bean("simpleHessian")
    public HessianProxyFactoryBean helloClient() {
        HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
        factory.setServiceUrl("http://localhost:8080/SimpleHessianExp");
        factory.setServiceInterface(SimpleHessian.class);
        return factory;
    }
}
