package com.example.springcloudhystrixserviceprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 这俩玩意儿感觉一样{@see EnableHystrix EnableCircuitBreaker}
 *
 * http://127.0.0.1:7071/hystrix.stream 可以放入到dashboard中进行管理，或者可以使用chrome访问
 */
@SpringBootApplication
//@EnableHystrix
@EnableCircuitBreaker
public class SpringcloudHystrixServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudHystrixServiceProviderApplication.class, args);
	}

}
