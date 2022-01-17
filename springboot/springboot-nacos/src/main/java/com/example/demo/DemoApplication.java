package com.example.demo;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 dataId：这个属性是需要在Nacos中配置的Data Id。
 autoRefreshed：为true的话开启自动更新。

 */
@SpringBootApplication
@NacosPropertySource(dataId = "springboot-nacos-config.yml", autoRefreshed = true)
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@NacosInjected
	private NamingService namingService;

	@Value("${spring.application.name}")
	private String applicationName;

	@Value("${server.port}")
	private Integer serverPort;

	@Override
	public void run(String... args) throws Exception {
		// 通过Naming服务注册实例到注册中心
		namingService.registerInstance(applicationName, "192.168.7.6", serverPort);
	}
}
