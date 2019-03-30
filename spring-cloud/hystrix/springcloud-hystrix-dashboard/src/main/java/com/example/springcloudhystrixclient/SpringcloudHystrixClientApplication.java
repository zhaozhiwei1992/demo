package com.example.springcloudhystrixclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * http://127.0.0.1:10000/hystrix
 */
@SpringBootApplication
@EnableHystrixDashboard
public class SpringcloudHystrixClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudHystrixClientApplication.class, args);
	}
}
