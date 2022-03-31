package com.example.config;

import com.example.web.socket.HelloWorldHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Title: WebSocketConfig
 * @Package com/example/config/WebSocketConfig.java
 * @Description:
 * 第一步: 需要在一个配置类上使用@EnableWebSocket，并实现WebSocketConfigurer接口
 * @author zhaozhiwei
 * @date 2022/3/31 上午9:41
 * @version V1.0
 */
@Configuration
@EnableWebSocket
//@ComponentScan(basePackages = "com.example.web")
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(helloWorldHandler(), "/hello");
    }

    @Bean
    public HelloWorldHandler helloWorldHandler(){
        return new HelloWorldHandler();
    }

}
