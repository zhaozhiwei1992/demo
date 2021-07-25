package com.lx.demo.springbootlog.web.rest;

import com.lx.demo.springbootlog.service.TraceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.springbootlog.web.rest
 * @Description: 测试链路请求, 通过请求可以找到对应service
 * @date 2021/7/25 下午4:22
 */
@RestController
public class TraceResource {

    private static final Logger logger = LoggerFactory.getLogger(TraceResource.class);

    @Autowired
    private TraceService traceService;

    @GetMapping("trace")
    public String trace(){
        logger.info("当前请求方法: com.lx.demo.springbootlog.web.rest.TraceController.trace");
        return traceService.trace();
    }
}
