package com.example;

import com.example.config.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.client.RestTemplate;

/**
 * 加入feign来更便利访问远程服务
 * 注意：这里feign通过eureka注册中心来发现服务，一定要加入服务发现注解
 *
 * 排除没起作用，单独测试
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserServiceClientMain {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceClientMain.class, args);
    }

    /**
     * 当前服务resttemplate访问加入负载均衡
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
