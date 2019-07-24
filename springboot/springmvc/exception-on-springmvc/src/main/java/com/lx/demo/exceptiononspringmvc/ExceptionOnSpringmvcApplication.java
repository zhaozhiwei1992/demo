package com.lx.demo.exceptiononspringmvc;

import com.lx.demo.exceptiononspringmvc.interceptor.DefaultHandlerInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @{link org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter} //这个是springboot中用来扩展拦截器的一种方式
 * {@see https://docs.spring.io/spring-boot/docs/2.0.8.RELEASE/reference/htmlsingle/#boot-features-error-handling-mapping-error-pages-without-mvc}
 *
 * 通过测试，异常捕获优先级 内部异常 > advice异常 > 全局resolver捕获异常
 */
@SpringBootApplication
public class ExceptionOnSpringmvcApplication
		extends WebMvcConfigurerAdapter
		implements ErrorPageRegistrar {

	public static void main(String[] args) {
		SpringApplication.run(ExceptionOnSpringmvcApplication.class, args);
	}

	/**
	 * 引入自定义拦截器的一种方式
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new DefaultHandlerInterceptor());
	}

	/**
	 * springboot 中访问不到页面时拦截请求方式
	 * @param registry
	 */
	@Override
	public void registerErrorPages(ErrorPageRegistry registry) {
		registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404.html"));
	}
}
