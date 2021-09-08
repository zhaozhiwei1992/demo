package com.example.springbootskywalking.service;

import com.example.springbootskywalking.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootskywalking.service
 * @Description: TODO
 * @date 2021/9/8 下午5:01
 */
@Service
public class TestService2 {

    public static void staticMethod(String s1, int int1){
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
    }

    public void method(Object obj1){
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        method(new ArrayList(), 99);
    }

    public void method(List list1,int int1){
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
    }
}
