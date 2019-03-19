package com.lx.demo.exceptiononspringmvc;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * springmvc 中异常拦截器
 * https://docs.spring.io/spring/docs/5.0.0.RELEASE/spring-framework-reference/web.html
 * c+f exceptonhandler
 */
@RestControllerAdvice
public class SpringMVCControllerHandlerExceptionAdvicer {

    /**
     * 抛出异常后拦截
     * @param throwable
     * @return
     */
    @ExceptionHandler(value = {NullPointerException.class
            ,IllegalAccessException.class,
            IllegalStateException.class,
    })
    public Object handleNPE(
            Throwable throwable) {
        Map<String,Object> data = new HashMap<>();
        data.put("message",throwable.getMessage());
        return data;
    }
}
