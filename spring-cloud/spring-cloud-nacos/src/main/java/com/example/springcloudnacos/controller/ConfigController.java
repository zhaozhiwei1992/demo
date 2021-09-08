package com.example.springcloudnacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: ConfigController
 * @Package com/example/springcloudnacos/controller/ConfigController.java
 * @Description:
 * https://nacos.io/zh-cn/docs/quick-start-spring-cloud.html
 * 通过curl命令发送配置
 * curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId={应用名称}.properties&group=DEFAULT_GROUP&content=useLocalCache=true"
 * 获取:
 * curl http://localhost:8080/config/get
 * @author zhaozhiwei
 * @date 2021/6/24 上午11:31
 * @version V1.0
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }

    @Value("${spring.datasource.url:123}")
    private String url;

    @GetMapping("/url")
    public String url(){
        return url;
    }
}