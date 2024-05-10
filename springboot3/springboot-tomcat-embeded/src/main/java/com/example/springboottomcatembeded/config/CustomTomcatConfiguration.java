package com.example.springboottomcatembeded.config;

import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springboottomcatembeded.config
 * @Description: 两种方式都可以扩展port, 只是变更时机不同,
 * @date 2021/5/27 下午11:44
 */
@Configuration
public class CustomTomcatConfiguration {

    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandler() {
        return protocolHandler ->
                protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
    }

}
