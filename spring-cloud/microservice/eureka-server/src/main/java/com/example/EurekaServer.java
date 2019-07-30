package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Hello world!
 * 如果启动集群　eureka 假如启动参数
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServer {
    public static void main( String[] args ) {
        SpringApplication.run(EurekaServer.class, args);
    }
}
