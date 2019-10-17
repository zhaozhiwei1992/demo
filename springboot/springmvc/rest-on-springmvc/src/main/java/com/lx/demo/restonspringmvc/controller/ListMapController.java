package com.lx.demo.restonspringmvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description 大佬写点东西吧
 * @auther lx7ly
 * @create 2019-10-17 下午8:04
 */
@RestController
public class ListMapController {

    private static final Logger logger = LoggerFactory.getLogger(ListMapController.class);

    /**
     * 插入数据测试
     * curl -X POST -H "Content-Type:application/json;charset=UTF-8"
     * http://192.168.2.33:8080/insert\?province\=4300 -d '[{"name":"zhangsan"},{"name":"lisi"}]'
     *
     * 这里requestbody是必须的，否则会出现nosuchmethod
     * java.lang.NoSuchMethodException: java.util.List.<init>()
     * @param province
     * @param data
     * @return
     */
    @PostMapping("/insert")
    public String insert(@RequestParam String province, @RequestBody List<? extends Map> data){
        logger.info("province {}", province);
        logger.info("data {}", data);
        return "";
    }
}
