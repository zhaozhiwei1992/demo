package com.lx.demo.springbootschedulerquartz.jobs;

import org.jboss.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 自定义定时任务
 * 业务实现job接口，处理逻辑即可
 */
public class ScheduledJob implements Job {

    private static final Logger logger= Logger.getLogger(ScheduledJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //执行任务逻辑....
        logger.info("执行自定义定时任务");
    }
}