package com.lx.demo.springbootel;

import com.lx.demo.springbootel.config.ElConfig;
import com.lx.demo.springbootel.service.DemoElService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ElConfig.class);

		DemoElService demoElService = context.getBean(DemoElService.class);

		demoElService.outputResource();

		context.close();
	}

}
