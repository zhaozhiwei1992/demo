package com.example.domain;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/2/16 上午10:02
 */
public class Pig implements SpeakInterface{

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public void speak() {
        System.out.println("哼哼: " + message);
    }
}
