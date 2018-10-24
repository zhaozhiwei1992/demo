package com.lx.demo.readinglist;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * tomcat容器或者其他容器中如果没有web.xml或者servlet初始化类是没法启动springmvc的DispatcherServlet
 * so SpringBootServletInitializer是spring提供的Spring WebApplicationInitializer实现,
 *    可以支持dispatcherservlet, filter等并注册到servlet容器中
 */
public class ReadingListServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ReadingListApplication.class);
    }
}
