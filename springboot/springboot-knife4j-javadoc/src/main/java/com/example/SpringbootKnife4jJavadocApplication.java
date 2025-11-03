package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Title: SpringbootCacheApplication
 * @Package com/lx/demo/springbootcache/SpringbootCacheApplication.java
 * @Description:
 * 访问接口文档
 *
 * 启动项目后，访问以下地址查看接口文档：
 *
 * Knife4j UI: http://localhost:8080/doc.html
 *
 * OpenAPI JSON: http://localhost:8080/v3/api-docs
 * @author zhaozhiwei
 * @date 2022/9/23 上午9:54
 * @version V1.0
 */
@SpringBootApplication
@EnableCaching
public class SpringbootKnife4jJavadocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKnife4jJavadocApplication.class, args);
	}

}

