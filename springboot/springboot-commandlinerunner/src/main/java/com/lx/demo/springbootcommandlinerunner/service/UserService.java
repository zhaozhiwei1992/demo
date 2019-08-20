package com.lx.demo.springbootcommandlinerunner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService() {
        final StackTraceElement traceElement = Thread.currentThread().getStackTrace()[1];
        logger.info("{}#{}", traceElement.getClassName(), traceElement.getMethodName());
    }
}
