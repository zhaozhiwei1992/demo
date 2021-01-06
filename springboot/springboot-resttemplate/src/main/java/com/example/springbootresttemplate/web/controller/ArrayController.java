package com.example.springbootresttemplate.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootresttemplate.web.controller
 * @Description: TODO
 * @date 2021/1/6 下午3:38
 */
@RestController
@RequestMapping("/buscommon/task")
public class ArrayController {

    /**
     * 执行定时任务
     * @param args 参数
     * @return
     * curl -X POST -H "Content-Type=application/json;charset=utf-8" http://127.0.0
     * .1:8080/buscommon/task/executeTask\?args\=1,2,3,4
     */
    @PostMapping("/executeTask")
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
