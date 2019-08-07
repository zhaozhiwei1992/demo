package com.lx.demo.springbootpandect.config;

import com.lx.demo.springbootpandect.filter.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfig {

    /**
     *As described earlier, any Servlet or Filter beans are registered with the servlet container
     * automatically. To disable registration of a particular Filter or Servlet bean, create a registration
     * bean for it and mark it as disabled, as shown in the following example:
     * @return
     */
    @Bean
    public FilterRegistrationBean registration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        final CustomFilter customFilter = new CustomFilter();
        registration.setFilter(customFilter);
        registration.addUrlPatterns("/users/*");
        registration.addInitParameter("username", "zhangsan");
        registration.setName("filtername");
        registration.setOrder(1);
        //创建过滤器并干掉
//        registration.setEnabled(false);
        return registration;
    }
}
