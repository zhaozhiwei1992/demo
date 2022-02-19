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
 * @Title: JUnit3 Test Class.java.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/19 下午11:04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SystemConfig.class)
public class RunTest {

    /**
     *
     * Unsatisfied dependency expressed through field 'rabbit';
     * nested exception is org.springframework.beans.factory.BeanNotOfRequiredTypeException:
     * Bean named 'rabbit' is expected to be of type 'com.example.domain.Rabbit'
     * but was actually of type 'com.sun.proxy.$Proxy26'
     * 最好使用接口或者父类来注入，否则会出现类型转换异常, 如果开始写程序就多利用多态，加入aop改动也会更小
     */
    @Autowired
    @Qualifier("rabbit")
//    使用接口来引入更方便改造
//    private Rabbit rabbit;
    private Run rabbit;

    @Autowired
    @Qualifier("turtles")
    private Run turtles;

    @Test
    public void testRun() throws InterruptedException {

        Assert.notNull(rabbit, "rabbit is null");
        Assert.notNull(turtles, "turtles is null");

        rabbit.run();
        turtles.run();
    }
}