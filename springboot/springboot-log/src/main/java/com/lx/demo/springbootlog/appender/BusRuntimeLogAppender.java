package com.lx.demo.springbootlog.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: BusRuntimeLogAppender
 * @Package com/lx/demo/springbootlog/appender/BusRuntimeLogAppender.java
 * @Description: 使用该类作为日志的appender, 可以自定义一些实现, 并记录日志信息
 * @date 2021/12/6 下午5:54
 */
public class BusRuntimeLogAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private static final Map<String, Set<BusRunTimeLogBO>> joblogs = new ConcurrentHashMap<String,
            Set<BusRunTimeLogBO>>();

    @Override
    protected void append(ILoggingEvent eventObject) {
        String jobid = LogService.getJobid();
        if (!StringUtils.isEmpty(jobid)) {

            long time = eventObject.getTimeStamp();

            String level = eventObject.getLevel().toString();

            String message = eventObject.getFormattedMessage();

            String clazz = eventObject.getLoggerName();

            save2Cache(jobid, time, level, message, clazz);
        }
    }

    private void save2Cache(String jobid, long time, String level, String message, String clazz) {
        BusRunTimeLogBO bo = new BusRunTimeLogBO(jobid, time, level, message, clazz);
        if (!joblogs.containsKey(jobid)) {
            joblogs.put(jobid, new CopyOnWriteArraySet<BusRunTimeLogBO>());
        }
        joblogs.get(jobid).add(bo);
    }

    public static Set<BusRunTimeLogBO> getLogs(String jobid) {
        if (joblogs.containsKey(jobid)) {
            Set<BusRunTimeLogBO> result = new TreeSet<BusRunTimeLogBO>();
            result.addAll(joblogs.get(jobid));
            return result;
        }
        return null;

    }

}
