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

    private String name;

    public Cat() {
    }

    public Cat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void speak() {
        System.out.println("我叫: " + name + ", 我是JavaConfig配置进来的, com.example.config.AnimalsConfig.cat");
    }
}
