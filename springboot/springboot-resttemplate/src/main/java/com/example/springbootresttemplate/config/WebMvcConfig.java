package com.example.springbootresttemplate.config;

import com.example.springbootresttemplate.filter.CustomFilter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebMvcConfig{

	public WebMvcConfig() {
		System.out.println("init WebMvcConfig ...");
	}

	@Bean
	public RestTemplate loadBalancedRestTemplate(RestTemplateBuilder builder){
		return builder.build();
	}

	/**
	 *As described earlier, any Servlet or Filter beans are registered with the servlet container
	 * automatically. To disable registration of a particular Filter or Servlet bean, create a registration
	 * bean for it and mark it as disabled, as shown in the following example:
	 * @return
	 */
	@Bean
	public FilterRegistrationBean registration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		final CustomFilter customFilter = new CustomFilter();
		registration.setFilter(customFilter);
		registration.addUrlPatterns("/test/*");
		registration.setName("filtername");
		registration.setOrder(1);
		//创建过滤器并干掉
//        registration.setEnabled(false);
		return registration;
	}

}
