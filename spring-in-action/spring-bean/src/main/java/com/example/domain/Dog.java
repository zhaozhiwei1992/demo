package com.example.domain;


import org.springframework.stereotype.Component;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/16 上午9:09
 */
@Component
public class Dog implements SpeakInterface{
    public void speak() {
        System.out.println("汪汪, 我是自动装配进来的");
    }
}
