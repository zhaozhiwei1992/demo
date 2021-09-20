package com.lx.demo.restonspringmvc.service;

import com.lx.demo.restonspringmvc.controller.EchoController;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.restonspringmvc.service
 * @Description: TODO
 * @date 2021/9/10 下午2:48
 */
@Service
public class EchoService {

    public String username;

    /**
     * @data: 2021/9/10-下午2:50
     * @User: zhaozhiwei
     * @method: echo

     * @return: void
     * @Description: 测试多线程请求service, 并修改username, 会混乱, 采用threadlocal
     */
    public void echo(){
        final String threadName = Thread.currentThread().getName();
        final Map<String, Object> map = EchoController.userObjs.get();
//        String msg = "service. username: " + this.username + " threadName:" + threadName;
        String msg = "service. username: " + map.get("username") + " threadName:" + threadName;
        System.out.println(msg);

    }
}
