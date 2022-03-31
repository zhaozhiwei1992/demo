package com.example.config;

import com.example.filter.CorsFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

/**
 * @Title: SpringWebAppInitializer
 * @Package com/example/config/SpringWebAppInitializer.java
 * @Description:
 * 程序入口，由容器驱动
 * 参考 https://docs.spring.io/spring-framework/docs/4.3.22.RELEASE/spring-framework-reference/htmlsingle/#mvc-features
 * <<spring in action 4>>
 *
 * 原理:
 * javax.servlet.ServletContainerInitializer
 *    |
 *    |
 * org.springframework.web.SpringServletContainerInitializer
 *    |
 *    |
 * 寻找 org.springframework.web.WebApplicationInitializer实现, 从而引入springmvc
 * 部署到Servlet 3.0容器中的时候，容器会自动发现WebApplicationInitializer实现，并用它来配置Servlet上下文
 * @author zhaozhiwei
 * @date 2022/2/21 上午10:00
 * @version V1.0
 */
public class SpringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // GolfingAppConfig defines beans that would be in root-context.xml
        return new Class<?>[] { SystemConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // GolfingWebConfig defines beans that would be in golfing-servlet.xml
        return new Class<?>[] {
                WebSocketConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new CorsFilter()
        };
    }
}