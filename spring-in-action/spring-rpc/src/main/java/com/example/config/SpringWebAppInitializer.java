package com.example.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

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
                WebMvcConfig.class,
        };
    }

    /**
     * @Description: 增加hessian请求拦截
     *     <servlet>
     *         <servlet-name>hessian</servlet-name>
     *         <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     *         <init-param>
     *             <param-name>contextConfigLocation</param-name>
     *             <param-value>classpath:spring/hessian.xml</param-value>
     *         </init-param>
     *     </servlet>
     *     <servlet-mapping>
     *         <servlet-name>hessian</servlet-name>
     *         <url-pattern>*.hessian</url-pattern>
     *     </servlet-mapping>
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/", "*.hessian"};
    }

}