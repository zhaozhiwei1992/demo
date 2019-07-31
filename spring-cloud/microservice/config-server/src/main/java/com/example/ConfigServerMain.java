package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 激活configserver配置
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerMain {
    public static void main( String[] args ) {
        SpringApplication.run(ConfigServerMain.class, args);
    }
}
