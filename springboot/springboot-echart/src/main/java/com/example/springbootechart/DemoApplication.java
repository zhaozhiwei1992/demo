package com.example.springbootechart;

import com.example.springbootechart.web.resource.EchartsController;
import com.github.abel533.echarts.Option;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

//		final Option option = new EchartsController().test1();
//		System.out.println(option);
	}

}
