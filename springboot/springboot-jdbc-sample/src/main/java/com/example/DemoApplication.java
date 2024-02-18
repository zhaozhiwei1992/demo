package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Title: DemoApplication
 * @Package com/example/DemoApplication.java
 * @Description: 基础jdbc测试, 数据库性能测试
 * @author zhaozhiwei
 * @date 2024/2/18 下午9:03
 * @version V1.0
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		// 阻止应用程序退出
		while (true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
