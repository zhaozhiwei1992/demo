package com.lx.demo.springbootscheduler.spring.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 实现动态调整定时任务周期, 运行时调整
 */
@Slf4j
@Lazy(false)
@Component
@EnableScheduling
public class DynamicTimeTask implements SchedulingConfigurer {

    /**
     * 默认30s一次
     */
    private static final String DEFAULT_CRON = "*/30 * * * * ?";

    private String cron = DEFAULT_CRON;

    /**
     * 让定时任务支持多线程
     * @return
     */
    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(20);
    }

    /**
     * 定时任务配置信息mapper
     */
//    @Autowired
//    CronMapper cronMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //用于设置定时任务线程数，默认不设置的话为单线程
        scheduledTaskRegistrar.setScheduler(taskExecutor());

        scheduledTaskRegistrar.addTriggerTask(
                () -> log.info("dynamictask running ......, 当前时间: {}", new Date()),
                triggerContext -> {
                    // 任务触发，可修改任务的执行周期
                    log.info("task trigger, new cron {}", cron);

//                    这里的cron可以读取数据库配置
//                    String cron = cronMapper.getCron();

                    CronTrigger trigger = new CronTrigger(cron);
                    Date nextExec = trigger.nextExecutionTime(triggerContext);
                    return nextExec;
                });
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
