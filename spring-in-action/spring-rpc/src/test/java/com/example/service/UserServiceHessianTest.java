package com.example.service;

import com.caucho.hessian.client.HessianProxyFactory;
import com.example.config.SystemConfig;
import com.example.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfig.class)
public class UserServiceHessianTest {

    @Autowired
    @Qualifier("hessianClient")
    private UserService userService;

    @Test
    public void testFindUsers() {
    }

    /**
     * @Description: 先通过tomcat容器, 可以使用mavenplugin 暴露服务
     */
    @Test
    public void testFindOne() throws MalformedURLException {
//        方式1： 手动创建hessian客户端
//        final HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
//        String url = "http://localhost:8080/user.hessian";
//        final UserService userService = (UserService) hessianProxyFactory.create(UserService.class, url);

//        方式2: 直接使用 com.example.config.HessianRpcConfiguration.hessianClient提供客户端 (推荐)
        final User one = userService.findOne(1);

        Assert.notNull(one, "对象为空");
        Assert.isTrue(one.getId() == 1, "获取对象异常");
    }

    public void testSave() {
    }
}