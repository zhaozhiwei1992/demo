package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Title: SpringbootCacheApplication
 * @Package com/lx/demo/springbootcache/SpringbootCacheApplication.java
 * @Description:
 * @author zhaozhiwei
 * @date 2022/9/23 上午9:54
 * @version V1.0
 */
@SpringBootApplication
@EnableCaching
public class SpringbootSwaggerJavadocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSwaggerJavadocApplication.class, args);
	}

}

