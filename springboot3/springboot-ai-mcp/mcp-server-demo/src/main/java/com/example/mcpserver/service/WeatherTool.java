package com.example.mcpserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class WeatherTool {

    private static final Logger logger = LoggerFactory.getLogger(WeatherTool.class);

    @Tool(description = "根据城市名称查询当前天气")
    public String getWeather(@ToolParam(description = "城市名称，如北京、上海") String city) {
        logger.info("getWeather被请求, city={}", city);
        // 模拟逻辑，实际可以调第三方天气 API
        return String.format("%s：晴，25°C，湿度 60%%，适合出门", city);
    }

    @Tool(description = "计算两个数字的和")
    public int add(
            @ToolParam(description = "第一个数") int a,
            @ToolParam(description = "第二个数") int b) {
        logger.info("add被请求, a={}, b={}", a, b);
        return a + b;
    }
}