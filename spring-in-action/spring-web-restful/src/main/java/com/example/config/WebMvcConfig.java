package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @Title: WebMvcConfig
 * @Package com/example/config/WebMvcConfig.java
 * @Description:
 * ** Spring提供了两种方法将资源的Java表述形式转换为发送给客户端的表述形式：
 *    1. 内容协商（Content negotiation）：选择一个视图，它能够将模型渲染为呈现给客户端的表述形式；
 *    2. 消息转换器（Message conversion）：通过一个消息转换器将控制器所返回的对象转换为呈现给客户端的表述形式
 *
 * 内容协商的两个步骤：
 * 1．确定请求的媒体类型；
 * 2．找到适合请求媒体类型的最佳视图。
 * @author zhaozhiwei
 * @date 2022/3/7 上午10:19
 * @version V1.0
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.web")
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    /**
     * 解析流程:
     * ContentNegotiatingViewResolver将会考虑到Accept头部信
     * 息并使用它所请求的媒体类型，但是它会首先查看URL的文件扩展
     * 名, 如.json .xml等
     *
     * 如果根据文件扩展名不能得到任何媒体类型的话，那就会考虑请求中
     * 的Accept头部信息
     * 如果没有Accept头部信息，并且扩展名也无法提供帮助的
     * 话，ContentNegotiatingViewResolver将会使用“/”作为默认
     * 的内容类型，这就意味着客户端必须要接收服务器发送的任何形式的
     * 表述
     */
    @Bean
    public ViewResolver viewResolver(ContentNegotiationManager contentNegotiationManager){
        final ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        viewResolver.setContentNegotiationManager(contentNegotiationManager);
        return viewResolver;
    }

    /**
     * @Description: 描述
     * 配置ContentNegotiationManager的方法：
     * 直接声明一个ContentNegotiationManager类型的bean；
     * 通过ContentNegotiationManagerFactoryBean间接创建bean；
     * 重载WebMvcConfigurerAdapter的 configureContentNegotiation()方法
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        客户端没有明确的扩展名如.json,或者没有指定accept, 如application/json, 默认返回json
//        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        configurer.defaultContentType(MediaType.TEXT_HTML);
    }

    /**
     * @Description: 用bean的形式查找视图
     */
    @Bean
    public ViewResolver beanNameViewResolver(){
        final BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
        return beanNameViewResolver;
    }

    /**
     * @Description:
     * 如果返回为users则解析为json
     * 添加@ResponseBody注解, 跳过正常的模型/视图流程，这样就能让Spring将方法返回的List<User>转换为响应体
     */
//    @Bean
    public View users(){
        return new MappingJackson2JsonView();
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        要求DispatcherServlet将对静态资源的请求转发到Servlet容
//        器中默认的Servlet上，而不是使用DispatcherServlet本身来处理
//                此类请求
        configurer.enable();
    }
}
