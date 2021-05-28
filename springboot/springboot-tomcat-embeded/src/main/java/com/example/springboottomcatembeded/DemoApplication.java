package com.example.springboottomcatembeded;

import com.example.springboottomcatembeded.config.CustomTomcatConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);

		new SpringApplicationBuilder()
				.sources(DemoApplication.class, CustomTomcatConfiguration.class)
				.run(args);
	}

}
