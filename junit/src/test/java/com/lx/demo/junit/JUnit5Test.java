package com.lx.demo.junit;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * junit5开始注解发生改变引用也变成了 jupiter
 * junit5.@BeforeEach == junit4.@Before
 * junit5.@AfterEach == junit4.@After
 * junit5.@BeforeAll == junit4.@BeforeClass
 * junit5.@AfterAll == junit4.@AfterAll
 */
public class JUnit5Test {
    /**
     * 每个方法测试前执行, 针对方法级别
     * @throws Exception
     */
    @BeforeEach
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
    @AfterEach
    public void tearDown() throws Exception {
        System.out.println("关闭数据源!");
    }

    /**
     * 指定重复次数
     */
    @RepeatedTest(50)
    public void test50Times(){
        System.out.println("hello");
    }

    /**
     * junit5开始可以传递参数
     * @param i
     */
    @ParameterizedTest
    @ValueSource( ints = { 1, 2, 3, 4, 6})
    public void testParams(int i){
        Assert.assertTrue(i > -1);
    }
}
