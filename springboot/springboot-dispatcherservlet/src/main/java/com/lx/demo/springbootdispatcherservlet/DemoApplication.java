package com.lx.demo.springbootdispatcherservlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * curl -X GET http://localhost:8080/api/v0/index
	 * @param dispatcherServlet
	 * @return
	 */
//	@Bean
	public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet){
		return new ServletRegistrationBean(dispatcherServlet, "/api/v0/*");
	}

}
