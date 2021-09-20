package com.lx.demo.restonspringmvc.controller;

import com.lx.demo.restonspringmvc.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.restonspringmvc.controller
 * @Description: 并发测试多个进程访问同一个对象bean, 是否混乱
 * @date 2021/9/10 下午2:47
 */
@RestController
public class EchoController {

    @Autowired
    private EchoService echoService;

    public static final ThreadLocal<Map<String, Object>> userObjs = new ThreadLocal<Map<String, Object>>();

    @GetMapping("/echo")
    public void echo(){
        final String threadName = Thread.currentThread().getName();

        final Map<String, Object> map1 = userObjs.get();
        String username = "";
        if(!Objects.isNull(map1)){
            username = String.valueOf(map1.get("username"));
        }
        String msg = "username: " + username + " threadName:" + threadName;
//        String msg = "username: " + echoService.username + " threadName:" + threadName;

//        通过对象设置属性，单例下线程不安全
        echoService.username = threadName;

        final HashMap<String, Object> map = new HashMap<>();
        map.put("username", threadName);
        userObjs.set(map);

        System.out.println(msg);
        this.echoService.echo();
    }
}
