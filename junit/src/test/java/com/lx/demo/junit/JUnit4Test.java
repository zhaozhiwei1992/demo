package com.lx.demo.junit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * junit4 不需要再去继承testcase
 */
public class JUnit4Test {
    /**
     * 每个方法测试前执行, 针对方法级别
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        System.out.println("打开数据源!");
    }

    @Test
    public void testHelloWorld(){
        System.out.println("HelloWorld");
    }

    @Test
    public void testHelloWorld2(){
        System.out.println("HelloWorld2");
    }

    /**
     * 每个方法测试后执行
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        System.out.println("关闭数据源!");
    }

    @Test
    public void test100Times(){
        for (int i = 0; i < 20; i++) {
            Assert.assertTrue(i > -1);
        }
    }
}
