package com.lx.demo.springcloudconfigclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EchoEnvController {

    private final Environment environment;

    @Autowired
    public EchoEnvController(Environment environment) {
        this.environment = environment;
    }

    /**
     * 获取 springcore中 environment
     * http://127.0.0.1:9090/echo/env/server.port
     * @param name
     * @return
     */
    @GetMapping("/echo/env/{name}")
    public Map<String, String> getProperty(@PathVariable String name){
        String property = environment.getProperty(name);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put(name, property);
        return hashMap;
    }
}
