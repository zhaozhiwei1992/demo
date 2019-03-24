package com.example.springcloudribbonserviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudRibbonServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudRibbonServiceProviderApplication.class, args);
	}

}
