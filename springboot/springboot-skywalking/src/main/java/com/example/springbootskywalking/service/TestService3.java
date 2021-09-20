package com.example.springbootskywalking.service;

import org.springframework.stereotype.Service;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootskywalking.service
 * @Description: 测试嵌套方法匹配
 * @date 2021/9/17 上午11:13
 */
@Service
public class TestService3 {

    public void method1() throws InterruptedException {
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        Thread.sleep(3000);
        method2();
    }

    private void method2() throws InterruptedException {
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        Thread.sleep(1000);
        method3();
    }

    private void method3() throws InterruptedException {
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        Thread.sleep(2000);
    }
}
