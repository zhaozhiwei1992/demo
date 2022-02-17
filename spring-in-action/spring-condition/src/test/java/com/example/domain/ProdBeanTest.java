package com.example.domain;

import com.example.config.ProfileBeanConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/17 上午9:43
 */

public class ProdBeanTest{

    /**
     * @data: 2022/2/17-上午9:45
     * @User: zhaozhiwei
     * @method: prodBeanTest

     * @return: void
     * @Description:
     * 通过java api硬编码方式激活profile测试
     */
    @Test
    public void prodBeanTest(){
        //        https://docs.spring.io/spring-framework/docs/4.3.22.RELEASE/spring-framework-reference/htmlsingle/#beans-definition-profiles-enable
        // 1. 入口类，创建ApplicationContext
        final AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        // 2. 配置profile 为prod
        annotationConfigApplicationContext.getEnvironment().setActiveProfiles("prod");
        annotationConfigApplicationContext.register(ProfileBeanConfig.class);

        annotationConfigApplicationContext.refresh();

        Assert.isTrue(annotationConfigApplicationContext.containsBean("prodBean"), "prodBean 初始化失败");
//        final Object prodBean = annotationConfigApplicationContext.getBean("prodBean");
//        System.out.println(prodBean);
        annotationConfigApplicationContext.close();

    }
}