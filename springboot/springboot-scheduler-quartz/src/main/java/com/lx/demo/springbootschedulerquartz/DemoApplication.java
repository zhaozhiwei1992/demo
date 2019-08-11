package com.lx.demo.springbootschedulerquartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 参考: https://www.jianshu.com/p/a99b80021be6
 * Job: 被执行的任务
 * Scheduler: 负责调度任务，如：开始/结束一个调度
 * Trigger: 负责执行任务的触发器（这里我用的是CronTrigger可以用cron表达式去执行任务的触发器）如："0/5 * * * * ?"
 * //每五秒执行一次，具体使用方法可以查阅一下官网。
 * JobDetail: 封装Job，Scheduler真正调度的对象，包括Job名称，组等信息。
 * JobListener:  任务执行的监听器，可以监听到任务执行前、后以及未能成功执行抛出异常。
 *
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
