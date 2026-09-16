package com.example.mcpserver.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class WeatherTool {

    @Tool(description = "根据城市名称查询当前天气")
    public String getWeather(@ToolParam(description = "城市名称，如北京、上海") String city) {
        // 模拟逻辑，实际可以调第三方天气 API
        return String.format("%s：晴，25°C，湿度 60%%，适合出门", city);
    }

    @Tool(description = "计算两个数字的和")
    public int add(
            @ToolParam(description = "第一个数") int a,
            @ToolParam(description = "第二个数") int b) {
        return a + b;
    }
}