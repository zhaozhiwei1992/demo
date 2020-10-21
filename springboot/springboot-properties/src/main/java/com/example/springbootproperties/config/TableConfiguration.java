package com.example.springbootproperties.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * transfer.map.table1=xx
 */
@Configuration
@ConfigurationProperties(prefix = "transfer")
public class TableConfiguration {

    private Map<String, Object> tables = new HashMap<>();

    public Map<String, Object> getTables() {
        return tables;
    }
}
