package com.example.domain;

import com.example.condition.TestExistsCondition;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description:
 * 通过条件化配置初始化
 * @date 2022/2/17 上午11:00
 */
@Component
@Conditional(TestExistsCondition.class)
public class TestBean implements CommonBean{

    public TestBean() {
        System.out.println("This is TestBean");
    }
}
