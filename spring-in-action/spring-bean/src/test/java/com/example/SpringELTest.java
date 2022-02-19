package com.example;

import com.example.config.SystemConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example
 * @Description: TODO
 * @date 2022/2/19 下午5:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfig.class)
public class SpringELTest {

    @Value("#{'staticString'}")
    private String staticString;

    @Value("#{T(java.lang.Math).random()}")
    private String mathRandom;

    @Value("#{cat.getName()}")
    private String catName;

    @Test
    public void catNameTest(){

        System.out.println(mathRandom);
        System.out.println("catName: " + catName);
    }
}
