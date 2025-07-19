package com.example.demo.service;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

  @Tool(description = "Get weather information by city name")
  public String getWeather(@ToolParam(description = "City name") String cityName) {
    return "Sunny in " + cityName;
  }
}