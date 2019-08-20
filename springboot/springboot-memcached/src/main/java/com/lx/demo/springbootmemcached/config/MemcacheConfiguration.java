package com.lx.demo.springbootmemcached.config;

import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 换个方式初始化bean
 */
@Configuration
public class MemcacheConfiguration{

    @Autowired
    private MemcacheProperties memcacheProperties;

    @Bean
    public MemcachedClient memcachedClient() throws IOException {
        return new MemcachedClient(
                new InetSocketAddress(memcacheProperties.getIp(), memcacheProperties.getPort()));
    }
}
