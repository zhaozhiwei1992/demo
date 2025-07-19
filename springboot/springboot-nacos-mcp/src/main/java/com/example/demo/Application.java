package com.example.demo;

import com.example.demo.service.WeatherService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @Title: Application
 * @Package com/example/demo/Application.java
 * @Description:
 *     "webmvc-mcp-server": {
 *       "url": "http://localhost:8081/sse",
 *       "type": "sse",
 *       "alwaysAllow": [],
 *       "disabled": false
 *     },
 * @author zhaozhiwei
 * @date 2025/7/19 13:58
 * @version V1.0
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ToolCallbackProvider weatherTools(WeatherService weatherService) {
        return MethodToolCallbackProvider.builder().toolObjects(weatherService).build();
    }

}