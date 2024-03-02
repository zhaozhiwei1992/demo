package com.example.springbootdruid.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.math.BigInteger;
import java.util.stream.Stream;

@Component
@Aspect
public class DbConnectTimeCountAspect {

    private static final Logger logger = LoggerFactory.getLogger(DbConnectTimeCountAspect.class);

    ThreadLocal<Long> beginTime = new ThreadLocal<>();

    @Pointcut("execution(* com.example.springbootdruid.service.PolardbConnTestService.exec())")
    public void pointcut() {
    }

    @Around("pointcut()")
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
        watch.stop();
        logger.info("++++++++++++++++++++{}.{} 方法结束, 耗时{} ++++++++++++++++++++++++", tragetClassName, methodName, watch.getTotalTimeMillis());
        return object;
    }
}
