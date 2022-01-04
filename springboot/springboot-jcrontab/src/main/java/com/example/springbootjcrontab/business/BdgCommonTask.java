package com.example.springbootjcrontab.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 指标定时任务配置入口
 * jobclass gov.mof.fasp2.bdg.timertask.BdgCommonTask#executeTimer
 * 添加定时任务配置命名规则如上 类的全路径#execute类名
 * <p>
 * v3040 开始jobid作为实际实现对象, 如果命名符合约定, 配置定时任务执行全限定名时可以直接使用execute
 * <p>
 * 定时任务配置表: select * from FW_T_JOBDETAIL;
 */
public class BdgCommonTask {
    /**
     * @Fields log : 记录日志信息
     */
    private static final Logger logger = LoggerFactory.getLogger(BdgCommonTask.class);

    /**
     * 通用配置实现
     * 约定:
     * 1. jobid必须代表实际bean
     * 2. 定时任务配置指定定时任务时， code与jobid保持一致
     * <p>
     * 兼容集中部署方案，区划过多时分服务处理
     *
     * @param args
     * @throws Throwable
     */
    public static void execute(String args[]) throws Throwable {
        long s1 = System.currentTimeMillis();
        String beanStr = args[2];
        logger.info(beanStr + ":开始时间: {}", new Date());
        // 加锁执行
        Thread.sleep(1000);
        logger.info(beanStr + ":结束时间" + System.currentTimeMillis() + ",用时：" + (System.currentTimeMillis() - s1));
    }
}

