package com.lx.demo.springbootconditional.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WindowsService implements OSService{
    private static final Logger logger = LoggerFactory.getLogger(WindowsService.class);

    public WindowsService() {
        logger.info("windowsService init!");
    }

    @Override
    public void echo() {
        final StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        logger.info("{}#{}", element.getClassName(), element.getMethodName());
    }
}
