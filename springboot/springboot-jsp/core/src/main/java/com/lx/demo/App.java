package com.lx.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Hello world!
 * https://stackoverflow.com/questions/4732965/exposing-resources-from-jar-files-in-web-applications-tomcat7
 * There is a feature in servlet 3.0 that allows you to package resources (images, jsp, etc.) in a JAR file. What you
 * do is in your jar file, you create META-INF/resources and dump anything you want in there including directories
 * for structuring your resources. What happens is that META-INF/resources will be mapped to the docroot of your web
 * application.
 * <p>
 * When there is a clash of resource between your app and the JAR file, your apps resource will be returned. See this
 * <p>
 * Tomcat 7 supports Servlet 3 so it should support this feature
 *
 * springbootservletinitializer not necessary,
 */
@SpringBootApplication
public class App extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
