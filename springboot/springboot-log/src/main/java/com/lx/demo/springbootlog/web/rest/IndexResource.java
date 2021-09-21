package com.lx.demo.springbootlog.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexResource {
    private static final Logger logger = LoggerFactory.getLogger(IndexResource.class);

    /**
     *    <!-- 日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出 -->
     *
     *  springboot2.1.x动态调整日志级别, 以来actuator
     *  1. 获取现在日志级别:
     *   curl -X GET http://127.0.0.1:8080/actuator/loggers/com.lx.demo.springbootlog.web.rest.IndexResource
     *  2. 更新日志级别
     * curl -X POST http://127.0.0.1:8080/actuator/loggers/com.lx.demo.springbootlog.web.rest.IndexResource
     * -H "Content-Type:application/json;charset=utf-8" -d "{\"configuredLevel\":\"ERROR\"}"
     *  3. 下次获取就是以更改后为主，并且后台输出日志也已变更
     * @return
     */
    @GetMapping("/")
    public String index(){
        logger.trace("trace --> {}", Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.debug("debug --> {}", Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info("info --> {}", Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.warn("warn --> {}", Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.error("error --> {}", Thread.currentThread().getStackTrace()[1].getMethodName());
        return "xx";
    }
}
