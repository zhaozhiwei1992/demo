package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 *
 * 加入feign来更便利访问远程服务
 * 注意：这里feign通过eureka注册中心来发现服务，一定要加入服务发现注解
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
