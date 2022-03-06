package com.example.service;

import com.caucho.burlap.client.BurlapProxyFactory;
import com.caucho.hessian.client.HessianProxyFactory;
import com.example.domain.User;
import org.junit.Test;
import org.springframework.remoting.caucho.BurlapProxyFactoryBean;
import org.springframework.util.Assert;

import java.net.MalformedURLException;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.service
 * @Description: TODO
 * @date 2022/3/6 下午4:12
 */
public class UserServiceBurlapTest {


    @Test
    public void testFindUsers() {
    }

    @Test
    public void testFindOne() throws MalformedURLException {
        final BurlapProxyFactory hessianProxyFactory = new BurlapProxyFactory();
        String url = "http://localhost:8080/user.burlap";
        final UserService userService = (UserService) hessianProxyFactory.create(UserService.class, url);
        final User one = userService.findOne(1);

        Assert.notNull(one, "对象为空");
        Assert.isTrue(one.getId() == 1, "获取对象异常");
    }

    public void testSave() {
    }
}