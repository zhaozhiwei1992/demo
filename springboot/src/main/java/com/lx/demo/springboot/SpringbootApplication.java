package com.lx.demo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class SpringbootApplication {

	@ResponseBody
	@RequestMapping(value = "/sayHello")
	public String sayHello(){
		System.out.printf("Hello Boy");
		return "";
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}
