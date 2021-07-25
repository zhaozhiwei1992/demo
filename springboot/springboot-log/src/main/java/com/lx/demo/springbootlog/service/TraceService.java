package com.lx.demo.springbootlog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo.springbootlog.service
 * @Description: TODO
 * @date 2021/7/25 下午4:24
 */
@Service
public class TraceService {

    private static final Logger logger = LoggerFactory.getLogger(TraceService.class);

    public String trace(){
        logger.info("当前请求方法: com.lx.demo.springbootlog.service.TraceService.trace");
        return "";
    }
}
