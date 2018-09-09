package com.lx.demo.junit;

import junit.framework.TestCase;

/**
 * junit3 需要继承{@link TestCase}
 */
public class JUnit3Test extends TestCase {

    /**
     * 每个方法测试前执行, 针对方法级别
     * @throws Exception
     */
    @Override
    public void setUp() throws Exception {
        System.out.println("打开数据源!");
    }

    public void testHelloWorld(){
        System.out.println("HelloWorld");
    }

    public void testHelloWorld2(){
        System.out.println("HelloWorld2");
    }

    /**
     * 每个方法测试后执行
     * @throws Exception
     */
    @Override
    public void tearDown() throws Exception {
        System.out.println("关闭数据源!");
    }
}
