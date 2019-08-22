package com.lx.demo.springbootconditional.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinuxService implements OSService{

    private static final Logger logger = LoggerFactory.getLogger(LinuxService.class);

    public LinuxService() {
        logger.info("linuxService init!");
    }

    @Override
    public void echo() {
        final StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        logger.info("{}#{}", element.getClassName(), element.getMethodName());
    }
}
