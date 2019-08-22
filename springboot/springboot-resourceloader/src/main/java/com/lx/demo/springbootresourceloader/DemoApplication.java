package com.lx.demo.springbootresourceloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *curl -X GET http://localhost:8080/test.txt
 * 优先级顺序为：META/resources > resources > static > public
 */
@SpringBootApplication
public class DemoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * 增加资源目录
	 * curl -X GET http://localhost:8080/myres/testx.txt
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		//注意这里使用pathpattern时候如果用/**, 会把默认的覆盖掉，意味着resources那些都不可用
		registry.addResourceHandler("/myres/**").addResourceLocations("classpath:/myres/");
	}
}
