package com.example.web.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试configserver的一些特性
 * {@link RefreshScope 配置更新时得到特殊处理, 与/refresh配合使用}
 */
@RestController
@RefreshScope
public class ConfigProfilesController {

    @Value("${my.name}")
    private String name;

    /**
     * curl -X GET http://127.0.0.1:8080/whoami
     * my name is: lisi
     * @return
     */
    @GetMapping("/whoami")
    public String whoami(){
        return "my name is: " + name;
    }
}
