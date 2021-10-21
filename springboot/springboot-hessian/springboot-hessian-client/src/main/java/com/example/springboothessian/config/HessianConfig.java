package com.example.springboothessian.config;

import com.example.springboothessian.web.hessian.SimpleHessian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;

/**
 * @Title: HessianConfig
 * @Package com/example/springboothessian/config/HessianConfig.java
 * @Description: 通过RemoteAccessor代理当前客户端去请求hessian服务
 * @author zhaozhiwei
 * @date 2021/10/18 下午7:54
 * @version V1.0
 */
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
