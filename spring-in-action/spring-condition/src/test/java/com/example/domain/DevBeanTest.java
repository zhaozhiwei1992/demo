package com.example.domain;

import com.example.config.ProfileBeanConfig;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/17 上午9:40
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ProfileBeanConfig.class})
@ActiveProfiles("dev")
public class DevBeanTest extends TestCase {

    @Autowired
    private DevBean devBean;

    @Test
    public void devBeanTest(){
        Assert.notNull(devBean, "devBean is null");
    }
}