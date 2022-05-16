package com.lx.demo.springbootconditional.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @Title: CustomService
 * @Package com/lx/demo/springbootconditional/service/CustomService.java
 * @Description: 条件配置使用 - 线 可以同时支持驼峰和 -线配置
 * @author zhaozhiwei
 * @date 2022/5/16 下午4:29
 * @version V1.0
 */
@Component
@ConditionalOnProperty(prefix = "remote.rest.remote-info", name = "enabled", havingValue = "true")
public class CustomService {

    private static final Logger logger = LoggerFactory.getLogger(CustomService.class);

    public CustomService() {
        logger.info("customService init!");
    }
}
