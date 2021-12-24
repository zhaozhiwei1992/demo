package com.example.springbootresttemplate.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport implements ServletContextInitializer {

	public WebMvcConfig() {
		System.out.println("init WebMvcConfig ...");
	}

	@Bean
	public RestTemplate loadBalancedRestTemplate(RestTemplateBuilder builder){
		// httpClient连接配置
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

		// 连接超时
		clientHttpRequestFactory.setConnectTimeout(2000);
		// 数据读取超时时间
		clientHttpRequestFactory.setReadTimeout(3000);
		// 连接不够用的等待时间
		clientHttpRequestFactory.setConnectionRequestTimeout(10000);
		RestTemplate restTemplate  = builder.build();
		restTemplate.setRequestFactory(clientHttpRequestFactory);

		return restTemplate;
	}

	@Bean
	@Primary
	@Override
	public Validator mvcValidator() {
		return super.mvcValidator();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.removeIf(converter -> converter instanceof MappingJackson2XmlHttpMessageConverter);
    }

	/**
	 * 这里有个坑，SpringBoot2 必须重写该方法，否则静态资源无法访问
	 *
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/")
				.addResourceLocations("classpath:/resources/").addResourceLocations("classpath:/static/")
				.addResourceLocations("classpath:/public/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/buszuul/**").addResourceLocations("classpath:/templates/buszuul/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
//		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}

	/**
	 * Configure the given {@link ServletContext} with any servlets, filters,
	 * listeners context-params and attributes necessary for initialization.
	 *
	 * @param servletContext the {@code ServletContext} to initialize
	 * @throws ServletException if any call against the given {@code ServletContext}
	 *                          throws a {@code ServletException}
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		this.setServletContext(servletContext);
	}
}
