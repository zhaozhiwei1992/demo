package com.lx.demo.junit.mock;

import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.mock.env.MockEnvironment;

public class EnvironmentTest {

    /**
     * mock实现中没有把一些基础属性放入到里边
     */
    @Test
    public void testMockEnvironment(){
        Environment environment = new MockEnvironment();
        System.out.println(environment.getProperty("os.name"));
    }

    @Test
    public void testStandardEnvironment(){
        Environment environment = new StandardEnvironment();
        System.out.println(environment.getProperty("os.name"));
    }
}
