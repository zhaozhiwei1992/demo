package com.lx.demo.springbooti18n;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

/**
 * {@see org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration}
 *
 * 默认解析器通过请求中的accept-language处理
 * {@see AcceptHeaderLocaleResolver}
 */
@SpringBootApplication
public class DemoApplication{

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
