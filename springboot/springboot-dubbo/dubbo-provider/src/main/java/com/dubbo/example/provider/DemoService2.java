package com.dubbo.example.provider;

import org.springframework.stereotype.Service;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.dubbo.example.provider
 * @Description: 测试dubbo.Refrence能否引用普通Spring Service? 并不能
 * @date 2022/3/5 下午10:03
 */
@Service
public class DemoService2 {

    public String sayHello(String name) {
        System.out.println("annotation by spring Service");
        return null;
    }
}
