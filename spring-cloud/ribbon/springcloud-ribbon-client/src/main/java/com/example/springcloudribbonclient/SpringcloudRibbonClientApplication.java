package com.example.springcloudribbonclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RibbonClients(
		@RibbonClient(name = "${service-provider.name}")
)
@EnableDiscoveryClient
public class SpringcloudRibbonClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudRibbonClientApplication.class, args);
	}

	/**
	 * 如果使用eureka必須  加入loadbalance才可以用
	 * @return
	 */
	@LoadBalanced // RestTemplate 的行为变化
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
