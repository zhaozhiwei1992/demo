package com.example.config;

import com.example.filter.ServletIndexFilter;
import com.example.web.servlet.IndexServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description:
 * {@see com.example.config.SpringWebAppInitializer}
 * 按照AbstractAnnotationConfigDispatcherServletInitializer的定义，它会创建DispatcherServlet和
 * ContextLoaderListener。但是，如果你想在Web容器中注册其他的Servlet、Filter或Listener的话
 * 只需创建一个新的初始化器就可以了。最简单的方式就是实现Spring的WebApplicationInitializer接口
 * @date 2022/2/25 下午3:07
 */
public class CustomServletInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        servletContext
//                注册servlet
                .addServlet("customServlet", IndexServlet.class)
//                映射servlet
                .addMapping("/index");

//        增加过滤器
        servletContext.addFilter("customFilter", ServletIndexFilter.class)
                .addMappingForUrlPatterns(null, false, "/index/**");

        // 文件上传，方式1, 手动创建dispatcherServlet
//        final DispatcherServlet dispatcherServlet = new DispatcherServlet();
//        final ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcherServlet",
//                dispatcherServlet);
//        dynamic.addMapping("/");
//        dynamic.setMultipartConfig(new MultipartConfigElement("/tmp"));

    }
}
