package com.example.springbootbean.service;

import org.springframework.stereotype.Component;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootbean.service
 * @Description: TODO
 * @date 2021/6/24 上午10:39
 */
@Component
public class Dog implements Animal{
    @Override
    public String echo() {
        return "one one";
    }

    @Override
    public String toString() {
        return "this is dog Bean";
    }
}
