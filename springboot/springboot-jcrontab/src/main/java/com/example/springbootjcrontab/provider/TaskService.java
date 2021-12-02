package com.example.springbootjcrontab.provider;

import org.jcrontab.CronTask;
import org.jcrontab.Crontab;
import org.jcrontab.log.Log;
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
        crontab.setProperty("org.jcrontab.log.Logger", "com.example.springbootjcrontab.jcrontab.log.JdbcExecLogger");
    }
    /**
     * 执行定时任务
     * @param args 参数
     * 手动触发:
     *  curl -X POST -H "Content-Type=application/json;charset=utf-8" http://127.0.0.1:8080/buscommon/task/executeTask\?args\=1,bdg,bdg.timertask.SyncIndexDataTask,com.example.springbootjcrontab.business.BdgCommonTask%23execute
     *  这里请求#要编码 %23, 否则无法解析
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
//                crontab.onceTask(className, methodName, args);
//                重写执行方法
                onceTask(className, methodName, args);
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

    /**
     * 释放锁，特殊情况锁不释放使用
     * curl -X POST -H "Content-Type=application/json;charset=utf-8" http://127.0.0.1:8007/buscommon/task/releaseLock
     * \?args\=1,bdg,bdg.timertask.ZJPayVoucherBillSyncTask,gov.mof.fasp2.bdg.timertask.BdgCommonTask%23execute
     * @param args 参数, 第3个参数为锁id
     * @return
     */
    @PostMapping("/releaseLock")
    public Map<String, Object> releaseLock(String[] args) {
        Map<String, Object> result = new HashMap<>();
        String appid = args[1];
        String lockName = appid + "_" + args[2]+"_fw";
        return result;
    }

    public synchronized int onceTask(String strClassName, String strMethodName, String[] strExtraInfo) {
        int iTaskID = -99999;
        String appid = strExtraInfo[1];
        final String lockName = appid + "_" + strExtraInfo[2]+"_fw";
        String params = "";
        try {
            CronTask newTask = new CronTask() {
                @Override
                public void runTask() {
//                    try {
                        super.runTask();
//                    } finally {
//                        LockStore.releaseLock(lockName);
//                    }
                }
            };
            newTask.setParams(crontab, iTaskID, strClassName, strMethodName, strExtraInfo);
            int lastDot = strClassName.lastIndexOf(".");
            if (lastDot > 0 && lastDot < strClassName.length()) {
                String classOnlyName = strClassName.substring(lastDot + 1);
                newTask.setName(classOnlyName);
            }

            newTask.setName("ServiceCrontask-" + iTaskID);
            newTask.start();
            if (strExtraInfo != null && strExtraInfo.length > 0) {
                for (int i = 0; i < strExtraInfo.length; ++i) {
                    params = params + strExtraInfo[i] + " ";
                }
            }

            Log.info(strClassName + "#" + strMethodName + " " + params);
            return iTaskID;
        } catch (Exception var10) {
            Log.error("Smth was wrong with" + strClassName + "#" + strMethodName + " " + params, var10);
            throw var10;
        }
    }

}
