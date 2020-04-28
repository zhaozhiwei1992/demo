package com.lx.demo.springbootfeign.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.math.BigInteger;
import java.util.stream.Stream;

@Component
@Aspect
public class RestApiRequestInfo {
    private static final Logger log = LoggerFactory.getLogger(RestApiRequestInfo.class);

    @Around("execution(* com.lx.demo.springbootfeign.HelloClientFeign.*(..))")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {
        String tragetClassName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        // 创建一个计时器
        StopWatch watch = new StopWatch();
        // 计时器开始
        watch.start();

        log.info("++++++++++++++++++++" + tragetClassName + "." + methodName + " 方法开始 ++++++++++++++++++++++++");

        final MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        final String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Stream.iterate(BigInteger.ZERO, bigInteger -> bigInteger.add(BigInteger.ONE)).limit(parameterNames.length).forEach(indexOf ->{
            final String parameterName = parameterNames[indexOf.intValue()];
            final Object argValue = args[indexOf.intValue()];
            System.out.printf("参数名称 %s, 参数值: %s \n", parameterName, argValue);
        });
//        Stream.of(parameterNames).forEach(paramete -> {
//            final int indexOf = ArrayUtils.indexOf(parameterNames, paramete);
//            final Object argValue = args[indexOf];
//            System.out.printf("参数名称 %s, 参数值: %s \n", paramete, argValue);
//        });

        // 执行方法
        Object object = joinPoint.proceed();
        log.info(object.toString());
        log.info("++++++++++++++++++++" + tragetClassName + "." + methodName + " 方法结束 ++++++++++++++++++++++++");
        return object;
    }
}
