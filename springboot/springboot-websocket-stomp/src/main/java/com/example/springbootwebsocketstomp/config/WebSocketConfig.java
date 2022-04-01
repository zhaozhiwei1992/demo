package com.example.springbootwebsocketstomp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Title: WebSocketConfig
 * @Package com/example/springbootwebsocketstomp/config/WebSocketConfig.java
 * @Description:
 * 步骤1: @EnableWebSocketMessageBroker注解能够在WebSocket之上启用STOMP
 * @author zhaozhiwei
 * @date 2022/3/31 下午6:27
 * @version V1.0
 */
@Configuration
//@AutoConfigureAfter(RabbitmqProperties.class)
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * @data: 2022/3/31-下午6:36
     * @User: zhaozhiwei
     * @method: registerStompEndpoints
      * @param stompEndpointRegistry :
     * @return: void
     * @Description: 描述
     * 重载了registerStompEndpoints()方法，将“/marcopolo”注册为STOMP
     * 端点。这个路径与之前发送和接收消息的目的地路径有所不同。这是一个端点，
     *
     * 重要: 客户端在订阅或发布消息到目的地路径前，要连接该端点。
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        //注册一个Stomp的端点,并指定使用SockJS协议。
//        前端通过/socket来和后端建立连接, 连接点 必须
        stompEndpointRegistry.addEndpoint("/socket").withSockJS();
    }

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    /**
     * @data: 2022/3/31-下午6:38
     * @User: zhaozhiwei
     * @method: configureMessageBroker
      * @param registry :
     * @return: void
     * @Description: 描述
     *
     * 方法是可选的，如果不重载它的话，将会自动配置一个简单
     * 的内存消息代理，用它来处理以“/topic”为前缀的消息
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //客户端订阅路径前缀（基于内存的STOMP消息代理）,
        registry.enableSimpleBroker(
                "/sub/"
                , "/queue/"
        );

//      注: 开启两个代理数据会翻倍
//      configureMessageBroker()方法的第一行代码启用了STOMP代理中继（broker relay）功能，
//      并将其目的地前缀设置为“/topic”和“/queue”

//      只有client02能收到返回，因为client01订阅了/sub/*
//        registry.enableStompBrokerRelay("/topic/", "/queue/")
//                .setRelayHost(rabbitmqProperties.getHost())
//                .setRelayPort(Integer.parseInt(rabbitmqProperties.getPort()))
//                .setClientLogin(rabbitmqProperties.getUsername())
//                .setClientPasscode(rabbitmqProperties.getPassword());

        //服务端点请求前缀, 客户端发送消息时候需要/request前缀, 转发到 @MessageMapping
//      function sendMessage() {
//              stompClient.send("/request/send", {}, message);
//      }
        registry.setApplicationDestinationPrefixes("/request");
    }
}