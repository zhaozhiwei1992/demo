package com.example.domain;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/19 下午10:34
 */
@Component
public class Rabbit implements Run{

    @Override
    public void run() throws InterruptedException {
        System.out.println("我是 [兔子], I,m Running...");
        final int i = new Random().nextInt(10);
        if(i < 9){
//            小于6秒可以跑完
           Thread.sleep(i * 1000);
        }else{
            throw new RuntimeException("超过9秒累死了");
        }
    }
}
