package com.example.domain;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.domain
 * @Description: TODO
 * @date 2022/3/7 下午2:58
 */
public class Error {

    private String code;
    private String message;

    public Error(String code) {
        this.code = code;
    }

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Error() {
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
