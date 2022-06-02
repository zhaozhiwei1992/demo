package com.lx.demo.springbootschedulerquartz.controller;

import com.lx.demo.springbootschedulerquartz.jobs.ScheduledJob;
import com.lx.demo.springbootschedulerquartz.service.SchedulerManager;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/quartz")
public class QuartzController {

    @Autowired
    public SchedulerManager myScheduler;

    /**
     * curl -X GET http://localhost:8080/quartz/job2
     * <p>
     * 效果:
     * jobToBeExecuted
     * Job : group2.job2 is going to start...
     * 2019-08-11 17:57:15.298  INFO 7976 --- [ryBean_Worker-1] c.l.d.s.jobs.ScheduledJob                : 执行自定义定时任务
     * jobWasExecuted
     * Job : group2.job2 is finished...
     * jobToBeExecuted
     * Job : group2.job2 is going to start...
     * 2019-08-11 17:57:20.003  INFO 7976 --- [ryBean_Worker-2] c.l.d.s.jobs.ScheduledJob                : 执行自定义定时任务
     * jobWasExecuted
     * Job : group2.job2 is finished...
     * jobToBeExecuted
     * Job : group2.job2 is going to start...
     * 2019-08-11 17:57:25.002  INFO 7976 --- [ryBean_Worker-3] c.l.d.s.jobs.ScheduledJob                : 执行自定义定时任务
     * jobWasExecuted
     * Job : group2.job2 is finished...
     *
     * @return
     */
    @RequestMapping(value = "/job2", method = RequestMethod.GET)
    public String scheduleJob2() {
        try {
            //每五秒执行一次
            myScheduler.startJob("0/5 * * * * ?", "job2", "group2", ScheduledJob.class);
            //0 0/5 14 * * ?在每天下午2点到下午2:55期间的每5分钟触发
            //0 50 14 * * ?在每天下午2点50分5秒执行一次
//            myScheduler.startJob("5 50 14 * * ?","job2","group2", ScheduledJob.class);
            return "启动定时器成功";
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "启动定时器失败";
    }

    @RequestMapping(value = "/del_job2", method = RequestMethod.GET)
    public String deleteScheduleJob2() {
        try {
            myScheduler.deleteJob("job2", "group2");
            return "删除定时器成功";
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return "删除定时器失败";
    }

    /**
     * 启动配置或者数据库中所有定时任务
     *
     * @return
     */
    @GetMapping("/all")
    public String startSchedulerJobAll() {
        // 这里可以是任意地方配置
        final ArrayList<Map<String, String>> jobList = new ArrayList<>();
        final HashMap<String, String> job1 = new HashMap<>();
        job1.put("cron", "0/5 * * * * ?");
        job1.put("jobName", "com.lx.demo.springbootschedulerquartz.business.Job1#execute");
        job1.put("jobGroup", "group1");
        jobList.add(job1);

        final HashMap<String, String> job2 = new HashMap<>();
        job2.put("cron", "0/5 * * * * ?");
        job2.put("jobName", "com.lx.demo.springbootschedulerquartz.business.Job2#execute");
        job2.put("jobGroup", "group1");
        jobList.add(job2);

        jobList.forEach(jobMap -> {
            try {
                myScheduler.startJob(jobMap.get("cron"), jobMap.get("jobName"), jobMap.get("jobGroup"),
                        ScheduledJob.class);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

        });
        return "启动定时器成功";
    }

}
