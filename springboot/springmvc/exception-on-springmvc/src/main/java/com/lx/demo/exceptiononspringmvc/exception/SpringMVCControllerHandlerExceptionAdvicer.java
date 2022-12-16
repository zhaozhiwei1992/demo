package com.lx.demo.exceptiononspringmvc.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * springmvc 中异常拦截器
 * https://docs.spring.io/spring/docs/5.0.0.RELEASE/spring-framework-reference/web.html
 * c+f exceptonhandler
 * {@see https://docs.spring.io/spring-boot/docs/2.0.8.RELEASE/reference/htmlsingle/#boot-features-error-handling}
 *
 * 推荐方式 advice + exceptionhandler
 *
 * ControllerAdvice支持的限定范围：
 *
 * 按注解：@ControllerAdvice(annotations = RestController.class)
 * 按包名：@ControllerAdvice("org.example.controllers")
 * 按类型：@ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
 */
@RestControllerAdvice
public class SpringMVCControllerHandlerExceptionAdvicer {

    /**
     * 抛出异常后拦截
     * 这里如果匹配到NullPointerException异常，就不会再走全局异常
     *
     * 拦截通用异常不生效处理, 如:Exception.class
     * 在启动类中给@SpringBootApplication加入scanBasePackages = {“com.lx.demo.exceptiononspringmvc”}
     * @param throwable
     * @return
     */
    @ExceptionHandler(value = {
//            NullPointerException.class,
//            IllegalAccessException.class,
//            IllegalStateException.class,
            Exception.class
    })
    public Object handleNPE(
            Throwable throwable) {
        Map<String,Object> data = new HashMap<>();
        data.put("message", Objects.isNull(throwable.getMessage())? throwable.toString(): throwable.getMessage());
        return data;
    }
}
