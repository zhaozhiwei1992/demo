package com.example;

import com.example.config.SystemConfig;
import com.example.domain.User;
import com.example.service.AlertService;
import junit.framework.TestCase;
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
 * 测试JMS ActiveMQ
 * @date 2022/3/29 下午4:02
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SystemConfig.class})
public class AlertServiceImplTest extends TestCase {

    @Autowired
    @Qualifier("localAlertService")
    private AlertService alertService;

    @Test
    public void testSendUserAlert() {

        final User user = new User();
        user.setId(1);
        user.setName("zhangsan");
        user.setAge(18);
        user.setPassword("123");
        alertService.sendUserAlert(user);

//        发送会拦截到消息
//        com.example.service.UserAlertHandler.onMessage

    }

    @Test
    public void testReceiveUserAlert() {
//        getJavaTypeForMessage
        final User user = alertService.receiveUserAlert();
        Assert.assertTrue(1 == user.getId());
    }
}