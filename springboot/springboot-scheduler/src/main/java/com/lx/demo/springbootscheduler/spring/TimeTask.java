package com.lx.demo.springbootscheduler.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * @Scheduled 参数可以接受两种定时的设置，一种是我们常用的cron="*\/6****?",一种是 fixedRate = 6000，两种都表示每隔六秒打印一下内容。
        *
        *fixedRate 说明
        *
        *@Scheduled(fixedRate = 6000) ：上一次开始执行时间点之后6秒再执行
        *@Scheduled(fixedDelay = 6000) ：上一次执行完毕时间点之后6秒再执行
        *@Scheduled(initialDelay = 1000, fixedRate = 6000) ：第一次延迟1秒后执行，之后按 fixedRate 的规则每6秒执行一次
 */
@Slf4j
@Configuration
@EnableScheduling
@ConditionalOnProperty(prefix = "synchronous.bdg.scheduling", name = "enabled", havingValue = "true")
public class TimeTask extends ScheduledTaskRegistrar implements BeanFactoryPostProcessor{

    /**
     * 每3秒执行
     */
    @Scheduled(cron = "*/3 * * * * ?")
    public void cronTask(){
        log.info("{}, 当前时间: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), new Date());
    }

    /**
     * 5秒执行
     */
    @Scheduled(fixedRate = 5000)
    public void fixedRateTask(){
        log.info("{}, 当前时间: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), new Date());
    }

    /**
     * 5秒执行
     */
    @Scheduled(cron = "${spring.text.time:* */3 * * * ?}")
    public void dynamicFixedRateTask(){
        log.info("{}, 当前时间: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), new Date());
    }

    /**
     * 销毁当前bean
     * 通过继承org.springframework.scheduling.config.ScheduledTaskRegistrar的方式
     */
//    @PreDestroy
    @Override
    public void destroy() {
        log.info("{}, 当前时间: {}", Thread.currentThread().getStackTrace()[1].getMethodName(), new Date());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        // 直接销毁bean ,然后由spring去调用destroy方法
        configurableListableBeanFactory.destroyBean(this);
    }
}
