package com.example.domain;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/17 上午11:07
 */
public class TestBeanTest{

    @Test
    public void testBeanTest(){
        final AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        final ConfigurableEnvironment environment = annotationConfigApplicationContext.getEnvironment();
        final Map<String, Object> systemProperties = environment.getSystemProperties();
        systemProperties.put("test", "xx");
        annotationConfigApplicationContext.register(TestBean.class);
        annotationConfigApplicationContext.refresh();

        Assert.isTrue(annotationConfigApplicationContext.containsBean("testBean"), "testBean初始化失败");

        annotationConfigApplicationContext.close();

    }
}