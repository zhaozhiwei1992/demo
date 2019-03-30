package com.example.springcloudhystrixserviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class SpringcloudHystrixServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudHystrixServiceProviderApplication.class, args);
	}

}
