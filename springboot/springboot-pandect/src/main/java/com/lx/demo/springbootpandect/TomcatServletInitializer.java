package com.lx.demo.springbootpandect;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

/**
 * http://127.0.0.1:8080/springboot-pandect-0.0.1-SNAPSHOT/hello?name=zhangsan
 * war包部署需要这个东东
 */
//@Configuration
public class TomcatServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoApplication.class);
    }
}