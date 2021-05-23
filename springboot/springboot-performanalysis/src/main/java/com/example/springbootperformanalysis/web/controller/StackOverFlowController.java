package com.example.springbootperformanalysis.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootperformanalysis.web.controller
 * @Description: java.lang.StackOverflowError: null
 * at com.example.springbootperformanalysis.web.controller.StackOverFlowController.m2(StackOverFlowController
 * .java:23) ~[classes/:na]
 * Java程序中，每个线程都有自己的Stack Space(堆栈)。这个Stack Space 不是来自Heap的分配。所以Stack Space的大小不会受到-Xmx和-Xms的影响，这2个JVM参数仅仅是影响Heap的大小。
 * Stack Space用来做方法的递归调用时压入Stack Frame(栈帧)。所以当递归调用太深的时候，就有可能耗尽Stack Space，爆出StackOverflow的错误。
 * -Xss256k：设置每个线程的堆栈大小, java高版本至少得228k
 *
 * 设置后count直接少于2000
 * @date 2021/5/23 下午1:31
 */
@RestController
public class StackOverFlowController {

    private int count = 0;

    @GetMapping("/stack")
    public String stackOverFlow() {

        try {
            m2();
        }catch (Throwable e){
            e.printStackTrace();
            // stack depth: 16808
            System.out.println("stack depth: " + count);
            return "stack depth:" + count;
        }
        return "0";
    }

    private void m2() {
        count++;
        m2();
    }
}
