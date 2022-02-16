package com.example;

import com.example.config.AnimalsConfig;
import com.example.domain.Cat;
import com.example.domain.Dog;
import com.example.domain.Pig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
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

    private Cat cat;

    @Autowired
    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Test
    public void catTest(){
        Assert.notNull(cat, "cat is null");
        cat.speak();
    }

    @Autowired
    private Pig pig;

    @Test
    public void pigTest(){
        Assert.notNull(pig, "pig is null");
        pig.speak();
    }
}
