package com.example.nacosexample.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    /**
     * @data: 2021/1/12-下午3:12
     * @User: zhaozhiwei
     * @method: get

     * @return: boolean
     * @Description: 描述
     * 测试
     * 写入: curl -X POST "http://192.168.1.5:8848/nacos/v1/cs/configs?dataId=nacos-example.properties&group=DEFAULT_GROUP&content=useLocalCache=true"
     * 获取配置信息: curl http://localhost:8080/config/get
     */
    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }
}