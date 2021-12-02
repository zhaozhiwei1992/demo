package com.example.springbootjcrontab.jcrontab.log;

import org.jcrontab.log.Logger;
import org.jcrontab.log.NullLogger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JdbcLogger extends NullLogger implements Logger {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JdbcLogger.class);

    @Override
    public void init() {
        logger.info("init........");
    }

    @Override
    public void error(final Map<String, String> map, Throwable t) {

        final Map<Object, Object> log = new HashMap<>();

        log.put("appid", map.get("appId"));
        log.put("Jobname", map.get("name"));
        log.put("Jobdate", map.get("date"));
        log.put("Result", "失败");

        VectorWriter vw = new VectorWriter();
        t.printStackTrace(vw);

        log.put("Error", vw.toStrings());
        vw.close();

        setData(log);
    }

    @Override
    public void success(Map<String, String> map) {

        final Map<Object, Object> log = new HashMap<>();

        log.put("appid", map.get("appId"));
        log.put("Jobname", map.get("name"));
        log.put("Jobdate", map.get("date"));
        log.put("Result", "成功");
        log.put("Error", "");

        setData(log);
    }

    private void setData(Map map) {
        logger.info("写入定时任务调用日志: {}", map);
    }

}