package com.example.service;

import com.example.config.SystemConfig;
import com.example.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.service
 * @Description:
 *  注意: 跟com.example.service.AlertServiceImplTest基本一致，但是使用的客户端不同，一个是用本地服务，一个用的是rpc方式
 * @date 2022/3/6 下午4:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SystemConfig.class})
public class AlertServiceJmsRpcTest {

    @Autowired
    @Qualifier("jmsClient")
    private AlertService alertService;

    @Test
    public void testSendUserAlert() {

        final User user = new User();
        user.setId(1);
        user.setName("第三方的张三");
        user.setAge(18);
        user.setPassword("123");
        alertService.sendUserAlert(user);
    }

    @Test
    public void testReceieUserAlert() {
        final User user = alertService.receiveUserAlert();
        Assert.assertTrue(1 == user.getId());
    }
}