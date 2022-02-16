package com.example;

import com.example.config.AnimalsConfig;
import com.example.domain.Dog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example
 * @Description:
 * 1. 测试自动化装配
 * @date 2022/2/16 上午9:30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AnimalsConfig.class})
public class AnimalTest {

    @Autowired
    private Dog dog;

    @Test
    public void dogTest(){
        Assert.notNull(dog, "dog is null");
        dog.speak();
    }
}
