package com.example;

import org.springframework.boot.SpringApplication;

//@SpringBootApplication
/**
 * @Title: DemoApplication
 * @Package com/example/DemoApplication.java
 * @Description: 需求如下:
 * 100元以下, 不加分
 * 100元-500元 加100分
 * 500元-1000元 加500分
 * 1000元 以上 加1000分
 *
 * 一般写法就是if else
 * @author zhaozhiwei
 * @date 2023/3/22 下午3:31
 * @version V1.0
 */
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
