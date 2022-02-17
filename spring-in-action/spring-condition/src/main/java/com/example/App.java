package com.example;

import com.example.config.ProfileBeanConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.StandardEnvironment;

/**
 * spring-profile
 * 1. 创建不同的profile bean 激活，检查是否符合
 * spring-condition
 * spring-el
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
//        https://docs.spring.io/spring-framework/docs/4.3.22.RELEASE/spring-framework-reference/htmlsingle/#beans-definition-profiles-enable
        // 1. 入口类，创建ApplicationContext
        final AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        // 2. 配置profile 为prod
        annotationConfigApplicationContext.getEnvironment().setActiveProfiles("prod");
        annotationConfigApplicationContext.register(ProfileBeanConfig.class);

        annotationConfigApplicationContext.refresh();
        final Object prodBean = annotationConfigApplicationContext.getBean("prodBean");
        System.out.println(prodBean);
//        annotationConfigApplicationContext.refresh();

        annotationConfigApplicationContext.close();
    }
}
