package com.example.mcpserver.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

@Component
public class LocalToolService {

    public String calculateDistance(JsonNode input) {
        double lat1 = input.get("lat1").asDouble();
        double lng1 = input.get("lng1").asDouble();
        double lat2 = input.get("lat2").asDouble();
        double lng2 = input.get("lng2").asDouble();

        // 简化：球面距离公式
        double d = Math.sqrt((lat1-lat2)*(lat1-lat2) + (lng1-lng2)*(lng1-lng2)) * 111;
        return String.format("两点距离约 %.2f km", d);
    }
}