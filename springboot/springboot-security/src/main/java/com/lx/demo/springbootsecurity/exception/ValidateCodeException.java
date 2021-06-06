package com.lx.demo.springbootsecurity.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.springbootsecurity.exception
 * @Description: TODO
 * @date 2021/6/6 下午5:03
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String message) {
        super(message);
    }

    public ValidateCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
