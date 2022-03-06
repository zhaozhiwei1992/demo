package com.example.service;

import com.example.config.SystemConfig;
import com.example.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.service
 * @Description: TODO
 * @date 2022/3/6 下午4:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfig.class)
public class UserServiceTest{

    @Autowired
    private UserService userService;

    @Test
    public void testFindUsers() {
    }

    @Test
    public void testFindOne() {
        final User one = userService.findOne(1);
        Assert.notNull(one, "对象为空");
        Assert.isTrue(one.getId() == 1, "获取对象异常");
    }

    public void testSave() {
    }
}