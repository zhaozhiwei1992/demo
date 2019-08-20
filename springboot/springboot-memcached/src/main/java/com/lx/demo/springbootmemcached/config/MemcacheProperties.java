package com.lx.demo.springbootmemcached.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "memcache")
public class MemcacheProperties {
    private String ip;

    private int port;
}
