package com.example.springcloudconsulclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 为了省事， consul启动使用consul agent -dev 以开发模式启动，就可以直接连接了， 访问http://127.0.0.1:8500观察服务注册说明配置成功
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudConsulClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudConsulClientApplication.class, args);
	}

}
