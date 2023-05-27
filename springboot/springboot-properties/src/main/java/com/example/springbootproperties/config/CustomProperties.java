package com.example.springbootproperties.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component("customProperties")
public class CustomProperties {
	
	private static Properties props = new Properties();
	
	@Bean
	public Properties defaultKaptcha() throws Exception {

		// 读取配置文件
		try {
			props.load(CustomProperties.class.getClassLoader()
                .getResourceAsStream("captcha.properties"));
		}catch (Exception e) {
			e.printStackTrace();
		}

		return props;
 
	}
}