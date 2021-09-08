package com.example.springbootskywalking.service;

import com.example.springbootskywalking.domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootskywalking.service
 * @Description: TODO
 * @date 2021/9/8 下午5:00
 */
@Service
public class TestService1 {

    public static void  staticMethod(){
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        staticMethod("str1", 100, new HashMap(), new ArrayList(), new Object());
    }

    public static void staticMethod(String s1, int int1, Map map1, List list1, Object obj1){
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
    }

    public void method(){
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        method("String", 99);
    }

    public void method(String str1,int int1){
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final User user = new User();
        user.setId(99);
        user.setName("zhangsan");
        method(user, str1, int1);
    }

    public void method(User user1, String str1, int int1){
        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
    }
}
