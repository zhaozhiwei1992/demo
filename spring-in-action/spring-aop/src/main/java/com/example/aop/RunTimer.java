package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.aop
 * @Description:
 * 用来记录兔子，乌龟跑步时间
 * 1. RunTimer 作为计时器， 在spring中是个切面
 *
 * <aop:aspectj-autoproxy>
 * @date 2022/2/19 下午10:50
 */
@Aspect
// *必须声明为bean 拦截器才会生效
//@Component
public class RunTimer {

    @Pointcut("execution (** com.example.domain..*.run())")
    public void runPoint(){}

    @Before("runPoint()")
    public void start(){
        System.out.println("-------------------------开始-------------------------");
    }

    @After("runPoint()")
    public void end(){
        System.out.println("-------------------------结束-------------------------");
    }

    @Around("runPoint()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        final long l = System.currentTimeMillis();
        System.out.println("开始时间: " + l);

        proceedingJoinPoint.proceed();

        final long l1 = System.currentTimeMillis();
        System.out.println("结束时间: " + l1);

        System.out.println("耗时: " + (l1-l));

    }

    @AfterThrowing("runPoint()")
    public void throwing(){
        System.out.println("糟糕, 赛跑出故障了..");
    }
}
