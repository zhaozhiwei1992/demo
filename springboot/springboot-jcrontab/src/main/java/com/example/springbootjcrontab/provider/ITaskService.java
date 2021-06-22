package com.example.springbootjcrontab.provider;

import java.util.Map;

public interface ITaskService {

    /**
     * 执行定时任务
     * @param args 参数
     * @return
     */
    public Map<String,Object> executeTask(String[] args);

}
