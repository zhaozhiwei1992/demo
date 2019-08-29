package com.lx.demo.springbootenum;

import com.lx.demo.springbootenum.web.convert.CustomStringToEnumConvertFactory;
import com.lx.demo.springbootenum.web.convert.StringToGenderConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DemoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * 1. 同时存在只有第一个生效
	 * 2. 单独增加converter灵活，但是乱
	 * @param registry
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToGenderConverter());

		//curl -X POST -F gender="男" -F type=1 http://127.0.0.1:8080/enum
        registry.addConverterFactory(new CustomStringToEnumConvertFactory());
	}
}
