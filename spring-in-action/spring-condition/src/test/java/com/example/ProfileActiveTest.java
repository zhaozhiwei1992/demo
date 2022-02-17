package com.example;

import com.example.config.ProfileBeanConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example
 * @Description: TODO
 * @date 2022/2/17 上午9:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProfileBeanConfig.class)
public class ProfileActiveTest {

    @Test
    public void devBeanTest(){

    }
}
