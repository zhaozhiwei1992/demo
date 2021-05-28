package com.example.springboottomcatembeded.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.ConfigurableTomcatWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springboottomcatembeded.config
 * @Description: 两种方式都可以扩展port, 只是变更时机不同,
 * @date 2021/5/27 下午11:44
 */
@Configuration
public class CustomTomcatConfiguration implements WebServerFactoryCustomizer<ConfigurableTomcatWebServerFactory> {

    @Override
    public void customize(ConfigurableTomcatWebServerFactory factory) {
//        class org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
        System.err.println(factory.getClass());

//       如果是tom 就进行设置
        if(factory instanceof TomcatServletWebServerFactory){

            // 这种方式是TomcatServletWebServerFactory创建好的补充, 也可以自己创建TomcatServletWebServerFactory bean
            TomcatServletWebServerFactory tomcatServletWebServerFactory = (TomcatServletWebServerFactory) factory;

            // 增加connector
            final Connector connector = new Connector();
            connector.setPort(9090);
            connector.setURIEncoding("UTF-8");

            tomcatServletWebServerFactory.addAdditionalTomcatConnectors(connector);
        }
    }

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();

        // 增加connector
        final Connector connector = new Connector();
        connector.setPort(9091);
        connector.setURIEncoding("UTF-8");

        tomcat.addAdditionalTomcatConnectors(connector);

        return tomcat;
    }
}
