package com.example.domain;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/16 上午9:52
 */
public class Cat implements SpeakInterface{

    public void speak() {
        System.out.println("喵喵, 我是JavaConfig配置进来的, com.example.config.AnimalsConfig.cat");
    }
}
