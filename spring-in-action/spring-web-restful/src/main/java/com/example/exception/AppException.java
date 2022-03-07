package com.example.exception;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.exception
 * @Description: TODO
 * @date 2022/2/26 上午11:59
 */
public class AppException extends Exception{

    // 错误号
    private String code;

    // 为最终用户提供的提示信息
    private String message;


    public AppException(String code, String message){
        this.code = code;
        this.message = message;
    }


    public String toString() {
        return "GeneralException [" + "Code:" + this.code + ", Message:" + this.message + "]";
    }
}
