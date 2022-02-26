package com.example.config;

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

    /**
     * @data: 2022/2/21-上午10:09
     * @User: zhaozhiwei
     * @method: getRootConfigClasses

     * @return: java.lang.Class<?>[]
     * @Description:
     * 大部分bean初始化放这里, 好分类
     *
     * <context-param>
     *         <param-name>contextConfigLocation</param-name>
     *         <param-value>/WEB-INF/root-context.xml</param-value>
     *     </context-param>
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        // GolfingAppConfig defines beans that would be in root-context.xml
        return new Class<?>[] { SystemConfig.class };
    }

    /**
     * @data: 2022/2/21-上午10:06
     * @User: zhaozhiwei
     * @method: getServletConfigClasses

     * @return: java.lang.Class<?>[]
     * @Description:
     * web相关配置在这里
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        // GolfingWebConfig defines beans that would be in golfing-servlet.xml
        return new Class<?>[] {
                WebMvcConfig.class,
//                ThymeleafWebMvcConfiguration.class
        };
    }

    /**
     * @data: 2022/2/21-上午10:07
     * @User: zhaozhiwei
     * @method: getServletMappings
    将一个或多个路径映
    射到DispatcherServlet上。在本例中，它映射的是“/”，这表示
    它会是应用的默认Servlet。它会处理进入应用的所有请求

    <servlet-mapping>
    <servlet-name>dispatcher</servlet-name>
    <url-pattern>/*</url-pattern>
    </servlet-mapping>
     * @return: java.lang.String[]
     * @Description: 描述
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /**
     * 扩展filter, 下述方式也可以
     * 通过com.example.config.CustomServletInitializer
     * springboot中使用org.springframework.boot.web.servlet.FilterRegistrationBean
     */
    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
//                new ServletIndexFilter()
        };
    }

    /**
     * web.xml中配置
     * <multipart-config>的默认值与MultipartConfigElement相
     * 同。与MultipartConfigElement一样，必须要配置的
     * 是<location>。
     */
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
//        这里可以通过MultipartConfigElement限制上传目录, 大小之类的
        // 文件上传，方式2
        registration.setMultipartConfig(new MultipartConfigElement("/tmp"));
    }
}