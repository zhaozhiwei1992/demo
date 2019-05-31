package com.lx.demo.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

/**
 * 参考: https://www.ctolib.com/docs/sfile/servlet-3.1-specification/docs/Servlet%20Context/4.4%20Configuration%20methods.html
 */
@WebListener("配置servlet")
public class ConfigServlet implements ServletContextListener {

    /**
     * 等价
     * <servlet>
     *     <servlet-name>Jersey REST Service</servlet-name>
     *     <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
     *     <!-- Register resources and providers under com.lx.demo 等  package. -->
     *     <init-param>
     *       <param-name>jersey.config.server.provider.packages</param-name>
     *       <param-value>com.lx.demo.web.rest;com.lx.demo</param-value>
     *     </init-param>
     *     <load-on-startup>1</load-on-startup>
     *   </servlet>
     *   <servlet-mapping>
     *     <servlet-name>Jersey REST Service</servlet-name>
     *     <url-pattern>/rest/*</url-pattern>
     *   </servlet-mapping>
     *   不用配置web.xml来初始化
     * @param servletContextEvent
     */
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("配置服务初始化开始....");
        ServletContext servletContext = servletContextEvent.getServletContext();
        ServletRegistration.Dynamic jerseyRestService = servletContext.addServlet("Jersey REST Service", "org.glassfish.jersey.servlet.ServletContainer");
        jerseyRestService.setLoadOnStartup(1);
        jerseyRestService.setInitParameter("jersey.config.server.provider.packages", "com.lx.demo.web.rest;com.lx.demo");
        jerseyRestService.addMapping("/rest/*");

        System.out.println("配置服务初始化结束...");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
