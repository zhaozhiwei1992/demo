package com.example.springcloudnacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Title: DemoApplication
 * @Package com/example/springcloudnacos/DemoApplication.java
 * @Description: TODO 大佬写点东西
 * https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html
 * @author zhaozhiwei
 * @date 2021/6/24 上午11:44
 * @version V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
