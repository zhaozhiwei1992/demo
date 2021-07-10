package com.lx.demo.aop;

import org.aspectj.lang.JoinPoint;

import java.util.Arrays;

/**
 * @Title: Logger
 * @Package com/lx/demo/aop/Logger.java
 * @Description: 测试代理
 * @author zhaozhiwei
 * @date 2021/7/10 上午10:56
 * @version V1.0
 */
public class Logger {

    public void test01() {
        System.out.println("测试出order属性的作用");
    }

    /**
     * @data: 2021/7/10-上午11:05
     * @User: zhaozhiwei
     * @method: before1
      * @param jp :
     * @return: void
     * @Description: 测试order顺序
     */
    public void before1(JoinPoint jp) {
        //获取get
        System.out.println("==前置通知==");
        System.out.println(Arrays.asList(jp.getArgs()));
        System.out.println(jp.getSignature().getName());
        System.out.println(jp.getTarget().getClass().getName());
        System.out.println("==前置通知==");
    }

    /**
     * @data: 2021/7/10-上午10:58
     * @User: zhaozhiwei
     * @method: test05
      * @param jp :
     * @return: void
     * @Description: 描述
    //1.前置通知
     */
    public void before2(JoinPoint jp) {
        //获取get
        System.out.println("==前置通知==");
        System.out.println(Arrays.asList(jp.getArgs()));
        System.out.println(jp.getSignature().getName());
        System.out.println(jp.getTarget().getClass().getName());
        System.out.println("==前置通知==");
    }

    /**
     * @data: 2021/7/10-上午10:59
     * @User: zhaozhiwei
     * @method: after
      * @param jp :
     * @return: void
     * @Description: 描述
    //2.后置通知
     */
    public void after(JoinPoint jp) {
        System.out.println("==后置增强==");
        System.out.println(Arrays.asList(jp.getArgs()));
        System.out.println(jp.getSignature().getName());
        System.out.println(jp.getTarget().getClass().getName());
        System.out.println("==后置增强==");

    }

    /**
     * @data: 2021/7/10-上午10:59
     * @User: zhaozhiwei
     * @method: returned
      * @param jp :
     * @param hs :
     * @return: void
     * @Description: 描述
    //3.返回通知
     */
    public void returned(JoinPoint jp, int hs) {
        System.out.println("==返回通知==");
        System.out.println("程序正常运行，正确的运行结果为==>" + hs);
        System.out.println("==返回通知==");
    }
    /**
     * @data: 2021/7/10-上午10:59
     * @User: zhaozhiwei
     * @method: exceptions
      * @param jp :
     * @param hs :
     * @return: void
     * @Description: 描述
    //4.异常通知
     */
    public void exceptions(JoinPoint jp, ArithmeticException hs) {
        System.out.println("==异常通知==");
        System.out.println("程序运行错误==>" + hs);
        System.out.println("==异常通知==");
    }

}