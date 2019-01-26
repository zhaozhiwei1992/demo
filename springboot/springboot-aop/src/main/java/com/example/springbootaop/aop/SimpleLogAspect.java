package com.example.springbootaop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 这种方式根据匹配的规则一刀切, 适合通用方案
 */
@Component
@Aspect
public class SimpleLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SimpleLogAspect.class);

    ThreadLocal<Long> beginTime = new ThreadLocal<>();

    /**
     * 切入点就是后面的表达式
     * @param joinPoint
     */
    @Before("execution(* com.example.springbootaop.controller..*.showLog2())")
    public void before(JoinPoint joinPoint){
        // 记录请求到达时间
        beginTime.set(System.currentTimeMillis());
        logger.info(String.format("当前请求: %s, 开始时间: %s", joinPoint.toString(), beginTime.get()));
    }

    @After("execution(* com.example.springbootaop.controller..*.showLog2())")
    public void after(JoinPoint joinPoint){
        logger.info(String.format("当前请求: %s, 耗时: %s", joinPoint.toString(), System.currentTimeMillis()-beginTime.get()));
    }
}
