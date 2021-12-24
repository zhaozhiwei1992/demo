package com.example.nacosexample;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import com.example.nacosexample.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Title: DemoApplication
 * @Package com/example/nacosexample/DemoApplication.java
 * @Description: ii. 通过 Spring Cloud 原生注解 @EnableDiscoveryClient 开启服务注册发现功能：
 * https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html
 * 查看注册后的服务
 * http://192.168.1.5:8848/nacos/#/serviceDetail?name=nacos-example&groupName=DEFAULT_GROUP
 * @author zhaozhiwei
 * @date 2021/1/12 下午3:21
 * @version V1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties({ApplicationProperties.class})
public class DemoApplication implements CommandLineRunner{

//	@LoadBalanced
//	@Bean
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private NacosDiscoveryProperties nacosDiscoveryProperties;

	@Override
	public void run(String... args) throws Exception {
//		NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
		// 通过Naming服务注册实例到注册中心 @EnableDiscoveryClient
//		namingService.registerInstance(applicationName, "127.0.0.1", serverPort);
		// 配置写入到metadata

	}

//	@Bean
//	public NacosDiscoveryProperties nacosProperties() {
//		Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
//		metadata.put("serviceprovince", serviceprovince);
//		return nacosDiscoveryProperties;
//	}
}
