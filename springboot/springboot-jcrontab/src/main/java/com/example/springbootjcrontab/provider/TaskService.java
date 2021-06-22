package com.example.springbootjcrontab.provider;

import org.jcrontab.Crontab;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@RestController
@RequestMapping("/buscommon/task")
public class TaskService implements ITaskService {

    private static Crontab crontab = Crontab.getInstance();
    static {
        crontab.setProperty("org.jcrontab.log.Logger", "gov.mof.fasp2.buscommon.task.jcrontab.log.JdbcLogger");
    }
    /**
     * 执行定时任务
     * @param args 参数
     * @return
     */
    @PostMapping("/executeTask")
    @Override
    public Map<String,Object> executeTask(String[] args) {
        Map<String,Object> result = new HashMap<>();
        try {
            String taskClass = args[3];
            int index = taskClass.indexOf("#");
            if (index > 0) {
                StringTokenizer tokenize = new StringTokenizer(taskClass, "#");
                String className = tokenize.nextToken();
                String methodName = tokenize.nextToken();
                result = new HashMap<>();
                crontab.onceTask(className, methodName, args);
                result.put("status", "success");
            }else{
                result.put("status","fail");
                result.put("message","任务类错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status","fail");
            result.put("message",e.getMessage());
        }
        return result;
    }
}
