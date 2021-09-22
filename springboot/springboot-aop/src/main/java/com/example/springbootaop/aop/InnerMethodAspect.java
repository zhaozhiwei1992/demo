package com.example.springbootaop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.math.BigInteger;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @Title: InnerMethodAspect
 * @Package com/example/springbootaop/aop/InnerMethodAspect.java
 * @Description: 内部嵌套拦截, 依赖业务类的实现
 * @author zhaozhiwei
 * @date 2021/9/22 下午3:36
 * @version V1.0
 */
@Component
@Aspect
public class InnerMethodAspect {

    private static final Logger logger = LoggerFactory.getLogger(InnerMethodAspect.class);

    ThreadLocal<Long> beginTime = new ThreadLocal<>();

    /**
     * 切入点就是后面的表达式
     * @param joinPoint
     */
    @Before("execution(* com.example.springbootaop.service..*.some*())")
    public void before(JoinPoint joinPoint){
        // 记录请求到达时间
        beginTime.set(System.currentTimeMillis());
        logger.info(String.format("当前请求: %s, 开始时间: %s", joinPoint.toString(), beginTime.get()));
    }

    @After("execution(* com.example.springbootaop.service..*.some*())")
    public void after(JoinPoint joinPoint){
        final String format = String.format("当前请求: %s, 耗时: %s", joinPoint.toString(),
                System.currentTimeMillis() - (Objects.isNull(beginTime.get())? 0:beginTime.get()));
        System.out.println(format);
    }

    @Around("execution(* com.example.springbootaop.service..*.some*())")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String tragetClassName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        // 创建一个计时器
        StopWatch watch = new StopWatch();
        // 计时器开始
        watch.start();

        logger.info("++++++++++++++++++++" + tragetClassName + "." + methodName + " 方法开始 ++++++++++++++++++++++++");

        final MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        final String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Stream.iterate(BigInteger.ZERO, bigInteger -> bigInteger.add(BigInteger.ONE)).limit(parameterNames.length).forEach(indexOf ->{
            final String parameterName = parameterNames[indexOf.intValue()];
            final Object argValue = args[indexOf.intValue()];
            System.out.printf("参数名称 %s, 参数值: %s \n", parameterName, argValue);
        });

        // 执行方法
        Object object = joinPoint.proceed();
        logger.info("++++++++++++++++++++" + tragetClassName + "." + methodName + " 方法结束 ++++++++++++++++++++++++");
        return object;
    }
}
