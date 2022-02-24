package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description:
 * 参考:
 * spring in action 4
 * https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.pdf
 * org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration
 *
 * 初始化, 这三个bean代码顺序不能换，或者指定 DependsOn注解， 否则初始化bean会出现依赖异常
 * 模板解析器
 * 模板引擎
 * 视图解析器
 * @date 2022/2/24 上午9:28
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.web")
public class ThymeleafWebMvcConfiguration{

    @Autowired
    private ApplicationContext applicationContext;


    /**
     * @Description: 视图解析器,
     */
    @Bean
    @DependsOn("templateEngine")
    public ThymeleafViewResolver thymeleafViewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    /**
     * @Description: 模板解析器
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(this.applicationContext);
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    @DependsOn("templateResolver")
    public TemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver) {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.addTemplateResolver(templateResolver);
        return engine;
    }

}
