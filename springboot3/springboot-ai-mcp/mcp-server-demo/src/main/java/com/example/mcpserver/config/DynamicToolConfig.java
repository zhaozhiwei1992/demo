package com.example.mcpserver.config;

import com.example.mcpserver.service.GenericToolDispatcher;
import com.example.mcpserver.service.ToolMeta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.definition.ToolDefinition;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class DynamicToolConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public ToolCallbackProvider jsonToolCallbackProvider(
            GenericToolDispatcher dispatcher) throws Exception {

        ClassPathResource resource = new ClassPathResource("tools.json");
        ToolMeta[] raw = objectMapper.readValue(resource.getInputStream(), ToolMeta[].class);
        List<ToolMeta> metas = Arrays.asList(raw);

        // 把 meta 缓存起来，dispatch 时查
        Map<String, ToolMeta> metaMap = metas.stream()
                .collect(Collectors.toMap(ToolMeta::name, m -> m));

        ToolCallback[] callbacks = metas.stream()
                .map(meta -> buildCallback(meta, dispatcher, metaMap))
                .toArray(ToolCallback[]::new);

        return () -> callbacks;
    }

    private ToolCallback buildCallback(
            ToolMeta meta,
            GenericToolDispatcher dispatcher,
            Map<String, ToolMeta> metaMap) {

        ToolDefinition definition = ToolDefinition.builder()
                .name(meta.name())
                .description(meta.description())
                .inputSchema(toJson(meta.inputSchema()))
                .build();

        // 关键：用一个 lambda / Method 包一层，把 toolName + meta 传给 dispatcher
        // 因为 MethodToolCallback 需要固定签名的方法，
        // 所以我们用一个"桥接对象"，方法签名统一吃 JsonNode 返回 String
        BridgeHandler bridge = new BridgeHandler(meta.name(), metaMap.get(meta.name()), dispatcher);

        Method method = ReflectionUtils.findMethod(BridgeHandler.class, "execute", JsonNode.class);

        return MethodToolCallback.builder()
                .toolDefinition(definition)
                .toolMethod(method)
                .toolObject(bridge)
                .build();
    }

    private String toJson(Object schema) {
        try {
            return objectMapper.writeValueAsString(schema);
        } catch (Exception e) {
            return "{}";
        }
    }

    /** 桥接：每个工具 new 一个，但逻辑全在 dispatcher 里 */
    public static class BridgeHandler {
        private final String toolName;
        private final ToolMeta meta;
        private final GenericToolDispatcher dispatcher;

        public BridgeHandler(String toolName, ToolMeta meta, GenericToolDispatcher dispatcher) {
            this.toolName = toolName;
            this.meta = meta;
            this.dispatcher = dispatcher;
        }

        public String execute(JsonNode input) {
            return dispatcher.dispatch(toolName, input, meta);
        }
    }
}