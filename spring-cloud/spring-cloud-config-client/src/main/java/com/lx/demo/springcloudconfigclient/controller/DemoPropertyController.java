package com.lx.demo.springcloudconfigclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查看服务端 demo-xx.properties属性
 * {@link RefreshScope}可以通过调用/actuator/refresh刷新
 * 注意: refreshscope注解加入后，属性刷新才可以被监控到
 */
@RestController
@RefreshScope
public class DemoPropertyController {
    @Value("${my.name}")
    private String myName;

    @GetMapping("/my-name")
    public String getMyName(){
        return this.myName;
    }
}
