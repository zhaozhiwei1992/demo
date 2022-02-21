package com.example.web.controller;

import com.example.config.SystemConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.web.controller
 * @Description: 通过junit测试home
 * @date 2022/2/21 上午9:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfig.class)
public class HomeControllerTest{

    @Test
    public void testHomePage(){

    }
}