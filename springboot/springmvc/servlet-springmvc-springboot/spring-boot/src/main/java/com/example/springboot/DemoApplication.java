package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 引入自定义autoconfig包, 这样在包中spring.factories中指定的自动配置类都可以启动
 * 提供了/comm/echo可以测试使用
 * curl http://127.0.0.1:8080/common/echo?name=xx
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
