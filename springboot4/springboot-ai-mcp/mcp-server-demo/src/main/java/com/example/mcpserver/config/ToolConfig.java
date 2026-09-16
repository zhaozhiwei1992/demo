package com.example.mcpserver.config;

import com.example.mcpserver.service.WeatherTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfig {

    @Bean
    public ToolCallbackProvider toolCallbackProvider(WeatherTool weatherTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherTool)
                .build();
    }
}