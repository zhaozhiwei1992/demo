package com.example.mcpserver.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GenericToolDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(GenericToolDispatcher.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebClient webClient;

    // 本地工具兜底（注入进来，不要 new）
    private final LocalToolService localToolService;

    public GenericToolDispatcher(WebClient.Builder webClientBuilder, LocalToolService localToolService) {
        this.webClient = webClientBuilder.build();
        this.localToolService = localToolService;
    }

    /**
     * 所有 MCP 工具最终都走到这个方法
     * toolName 区分路由，input 是模型传来的参数
     */
    public String dispatch(String toolName, JsonNode input, ToolMeta meta) {
        logger.info("dispatch tool={}, input={}", toolName, input);

        return switch (meta.type()) {
            case "http" -> callHttp(toolName, input, meta);
            case "local" -> callLocal(toolName, input, meta);
            default -> throw new IllegalArgumentException("未知工具类型: " + meta.type());
        };
    }

    private String callHttp(String toolName, JsonNode input, ToolMeta meta) {
        // 同步调用（MCP tool callback 是同步签名）
        // 如果你用 WebFlux MCP Server，这里可以 block() 或者直接用 RestTemplate
        try {
            String body = objectMapper.writeValueAsString(input);

            String response = webClient.method(org.springframework.http.HttpMethod.valueOf(
                            meta.httpMethod() != null ? meta.httpMethod() : "POST"))
                    .uri(meta.endpoint())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .onErrorReturn("调用失败: " + meta.endpoint())
                    .block();

            return response != null ? response : "无结果";

        } catch (Exception e) {
            logger.error("HTTP tool call failed: {}", toolName, e);
            return "工具调用异常: " + e.getMessage();
        }
    }

    private String callLocal(String toolName, JsonNode input, ToolMeta meta) {
        return switch (meta.handlerMethod()) {
            case "calculateDistance" -> localToolService.calculateDistance(input);
            default -> throw new IllegalArgumentException("未注册的本地方法: " + meta.handlerMethod());
        };
    }
}