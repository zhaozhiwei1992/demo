package com.example.mcpserver.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.chat.model.ChatModel;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatModel chatModel,
                                 ToolCallbackProvider mcpToolProvider) {
        return ChatClient.builder(chatModel)
                .defaultSystem("你是一个可以调用外部工具的智能助手")
                .defaultToolCallbacks(mcpToolProvider) // 注入 MCP 工具
                .build();
    }
}