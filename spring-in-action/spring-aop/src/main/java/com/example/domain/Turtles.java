package com.example.domain;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example
 * @Description: TODO
 * @date 2022/2/19 下午10:33
 */
@Component
public class Turtles implements Run{

    @Override
    public void run() throws InterruptedException {
        System.out.println("我是 [乌龟], I,m Running...");

        final int i = new Random().nextInt(10);
        if(i < 6){
//            小于6秒可以跑完
            Thread.sleep(i * 1000);
        }else{
            throw new RuntimeException("超过6秒累死了");
        }
    }
}
