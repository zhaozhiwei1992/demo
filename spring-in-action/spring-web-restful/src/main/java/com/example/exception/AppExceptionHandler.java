package com.example.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.exception
 * @Description: TODO
 * @date 2022/2/26 下午12:37
 */
@ControllerAdvice
public class AppExceptionHandler {

    /**
     * @Description: 异常捕获后返回到error界面
     * tiles.xml中要注册，否则解析不了
     */
    @ExceptionHandler(value = {AppException.class})
    public String appExceptionHandler(){
        return "errors";
    }
}
