package com.lx.demo.springbootscheduler.spring.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调整定时任务周期
 */
@Slf4j
@RestController
public class DynamicTaskController {

    @Autowired
    private DynamicTimeTask dynamicTimeTask;

    /**
     * 调整效果
     * 2019-08-11 16:29:30.001  INFO 5476 --- [pool-2-thread-1] c.l.d.s.spring.dynamic.DynamicTimeTask   : dynamictask running ......, 当前时间: Sun Aug 11 16:29:30 CST 2019
     * 2019-08-11 16:29:30.002  INFO 5476 --- [pool-2-thread-1] c.l.d.s.spring.dynamic.DynamicTimeTask   : task trigger, new cron *\/30****?
            *2019-08-11 16:30:00.000INFO 5476---[pool-2-thread-1]c.l.d.s.spring.dynamic.DynamicTimeTask   :
    dynamictask running ......,当前时间:
    Sun Aug 11 16:30:00CST 2019
            *2019-08-11 16:30:00.000INFO 5476---[pool-2-thread-1]c.l.d.s.spring.dynamic.DynamicTimeTask   :
    task trigger, new cron *\/30****?
            *2019-08-11 16:30:18.831INFO 5476---[nio-8080-exec-1]o.a.c.c.C .[Tomcat].[localhost].[/]:
    Initializing Spring
    DispatcherServlet 'dispatcherServlet'
            *2019-08-11 16:30:18.832INFO 5476---[nio-8080-exec-1]o.s.web.servlet.DispatcherServlet        :
    Initializing Servlet 'dispatcherServlet'
            *2019-08-11 16:30:18.838INFO 5476---[nio-8080-exec-1]o.s.web.servlet.DispatcherServlet        :
    Completed initialization
    in 6ms
     *2019-08-11 16:30:18.933INFO 5476---[nio-8080-exec-1]c.l.d.s.s.dynamic.DynamicTaskController  :原:*\/30****?,新:*\/3****?
            *2019-08-11 16:30:30.001INFO 5476---[pool-2-thread-2]c.l.d.s.spring.dynamic.DynamicTimeTask   :
    dynamictask running ......,当前时间:
    Sun Aug 11 16:30:30CST 2019
            *2019-08-11 16:30:30.001INFO 5476---[pool-2-thread-2]c.l.d.s.spring.dynamic.DynamicTimeTask   :
    task trigger, new cron *\/3****?
            *2019-08-11 16:30:33.000INFO 5476---[pool-2-thread-1]c.l.d.s.spring.dynamic.DynamicTimeTask   :
    dynamictask running ......,当前时间:
    Sun Aug 11 16:30:33CST 2019
            *2019-08-11 16:30:33.001INFO 5476---[pool-2-thread-1]c.l.d.s.spring.dynamic.DynamicTimeTask   :
    task trigger, new cron *\/3****?
            *2019-08-11 16:30:36.000INFO 5476---[pool-2-thread-3]c.l.d.s.spring.dynamic.DynamicTimeTask   :
    dynamictask running ......,当前时间:
    Sun Aug 11 16:30:36CST 2019
            *2019-08-11 16:30:36.001INFO 5476---[pool-2-thread-3]c.l.d.s.spring.dynamic.DynamicTimeTask   :
    task trigger, new cron *\/3****?
            *2019-08-11 16:30:39.000INFO 5476---[pool-2-thread-2]c.l.d.s.spring.dynamic.DynamicTimeTask   :
    dynamictask running ......,当前时间:
    Sun Aug 11 16:30:39CST 2019
            *2019-08-11 16:30:39.001INFO 5476---[pool-2-thread-2]c.l.d.s.spring.dynamic.DynamicTimeTask   :
    task trigger, new cron *\/3****?
     * @param expression
     * @return
     */
    @PutMapping("/cron")
    public String change(@RequestParam("expression") String expression){
        String preCron = dynamicTimeTask.getCron();
        dynamicTimeTask.setCron(expression);
        log.info("原: {}, 新: {}", preCron, dynamicTimeTask.getCron());
        return String.format("cron修改为: {}", dynamicTimeTask.getCron());
    }
}
