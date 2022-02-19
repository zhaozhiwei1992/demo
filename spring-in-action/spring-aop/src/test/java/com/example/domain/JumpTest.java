package com.example.domain;

import com.example.config.SystemConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/20 上午12:09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfig.class)
public class JumpTest {

    @Autowired
    @Qualifier("rabbit")
    private Run rabbit;

    @Test
    public void testJump(){
        Assert.notNull(rabbit, "rabbit is null");
        Assert.isTrue(rabbit instanceof Jump, "方法扩展失败");
//        这里会jump方法会成功调用
        ((Jump)rabbit).jump();
    }
}
