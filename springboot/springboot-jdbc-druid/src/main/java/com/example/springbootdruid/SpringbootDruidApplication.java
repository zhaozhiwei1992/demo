package com.example.springbootdruid;

import com.example.springbootdruid.config.LoadXmlBeanDefinitionReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;

/**
 * druid sql没生效
 * springboot通过importresource引入xml配置, 并且可以自定义reader
 * springboot中通过xml方式加载事务
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:applicationContext.xml"}, reader = LoadXmlBeanDefinitionReader.class)
public class SpringbootDruidApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDruidApplication.class, args);
	}

}
