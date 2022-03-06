package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = "com.example.controller")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * @data: 2022/2/21-上午10:26
     * @User: zhaozhiwei
     * @method: viewResolver

     * @return: org.springframework.web.servlet.ViewResolver
     * @Description: 配置jsp视图解析器
     */
    @Bean
    public ViewResolver viewResolver(){
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(org.springframework.web.servlet.view.JstlView.class);
//        这个配置可以让spring根据类型自动增加属性, 如果类型是User, 则增加map.put("user", new User());
        viewResolver.setExposeContextBeansAsAttributes(true);
        return viewResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        要求DispatcherServlet将对静态资源的请求转发到Servlet容
//        器中默认的Servlet上，而不是使用DispatcherServlet本身来处理
//                此类请求
        configurer.enable();
    }

}
