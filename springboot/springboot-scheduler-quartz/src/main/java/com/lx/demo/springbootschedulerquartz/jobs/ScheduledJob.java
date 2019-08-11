package com.lx.demo.springbootschedulerquartz.jobs;

import org.jboss.logging.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义定时任务
 * 站在job角度来说这里就是业务，当然也可以在内部封装处理自己的业务,累死business下的方式，实际包名只与jobName有关
 */
public class ScheduledJob implements Job {

    private static final Logger logger= Logger.getLogger(ScheduledJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //执行任务逻辑....
        logger.info("执行自定义定时任务");

        // 这里也可以利用反射，设计一个jar包， 数据库配置调度方式, 业务来实现业务
        // * * * * * execute  xxxxx.class#bussMethod
        //　业务全部在jobname指定包下执行即可
        final JobDetail jobDetail = jobExecutionContext.getJobDetail();
        final String jobName = jobDetail.getKey().getName();
        try {
            final String[] split = jobName.split("#");
            final Class<?> forName = Class.forName(split[0]);
            final Method method = forName.getMethod(split[1], null);
            method.invoke(forName.newInstance(), null);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}