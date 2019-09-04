package com.lx.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 主要配置多视图实现的视图解析器相关bean实例
 * <p>
 * https://www.it399.com/
 * <p>
 * 其实关键点在于两个：
 * 1、配置order属性
 * 2、配置viewnames属性
 * <p>
 * 注意：
 * return new ModelAndView("jsps/index");//或者return "jsps/index"
 * 对应 /WEB-INF/jsps/index.jsp
 * ==========================
 * 同理：
 * return "thymeleaf/index";//或者return “thymeleaf/index”
 * 对应 /WEB-INF/thymeleaf/index.html
 */
@Configuration
public class ViewResolverConfiguration {

    @Configuration//用来定义 DispatcherServlet 应用上下文中的 bean
    @EnableWebMvc
    @ComponentScan("com.csy.spring")
    public class WebConfig implements WebMvcConfigurer {
        @Bean
        public ViewResolver viewResolver() {
            InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//            resolver.setPrefix("/WEB-INF/");
//            resolver.setSuffix(".jsp");
//            resolver.setViewNames("jsps/*");
            resolver.setPrefix("/");
            resolver.setSuffix(".jsp");
            resolver.setViewNames("*");
            resolver.setOrder(2);
            return resolver;
        }
    }
}