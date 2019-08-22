package com.lx.demo.springbootresourceloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *curl -X GET http://localhost:8080/test.txt
 * 优先级顺序为：META/resources > resources > static > public
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
