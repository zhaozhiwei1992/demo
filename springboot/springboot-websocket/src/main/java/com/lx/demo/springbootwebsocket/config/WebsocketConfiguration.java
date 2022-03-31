package com.lx.demo.springbootwebsocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Title: WebsocketConfiguration
 * @Package com/lx/demo/springbootwebsocket/config/WebsocketConfiguration.java
 * @Description:
 *
 * 步骤2: 创建WebSocket配置类
 * @author zhaozhiwei
 * @date 2022/3/31 上午11:36
 * @version V1.0
 */
@Configuration
public class WebsocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}