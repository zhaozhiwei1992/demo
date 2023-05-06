package com.example.springbootjpa.exception;

/**
 * @Title: null.java
 * @Package: com.example.springbootjpa.exception
 * @Description: TODO
 * @author: zhaozhiwei
 * @date: 2023/5/6 下午1:57
 * @version: V1.0
 */
public class CustomException extends Exception{
    public CustomException(String msg) {
        super(msg);
    }
}
