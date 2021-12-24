package com.example.nacosexample.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Iterator;
import java.util.List;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport implements ServletContextInitializer {

	public WebMvcConfig() {
		System.out.println("init WebMvcConfig ...");
	}

	/**
	 * @data: 2021/12/24-下午3:27
	 * @User: zhaozhiwei
	 * @method: loadBalancedRestTemplate
	  * @param builder :
	 * @return: org.springframework.web.client.RestTemplate
	 * @Description: 这里如果使用了LoadBalance注解无法通过url方式访问
	 */
	@Bean
	@LoadBalanced
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

	@Bean
	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping requestMappingHandlerMapping = super.requestMappingHandlerMapping();
		// 解决不同窗口中的bean 发布.
		// requestMappingHandlerMapping.setDetectHandlerMethodsInAncestorContexts(true);
		return requestMappingHandlerMapping;
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for(Iterator<HttpMessageConverter<?>> it = converters.iterator();it.hasNext();){
            HttpMessageConverter<?> converter = it.next();
            if(converter instanceof MappingJackson2XmlHttpMessageConverter){
                it.remove();
            }
        }
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
