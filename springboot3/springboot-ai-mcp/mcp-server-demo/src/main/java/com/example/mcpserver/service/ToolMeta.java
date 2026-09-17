package com.example.mcpserver.service;

public record ToolMeta(
        String name,
        String description,
        String type,              // "http" | "local"
        String endpoint,          // HTTP 类型用
        String httpMethod,        // GET | POST
        String handlerBean,       // local 类型用：Spring Bean name
        String handlerMethod,     // local 类型用：方法名
        Object inputSchema
) {}