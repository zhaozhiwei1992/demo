package com.lx.demo.springbootschedulerquartz.business;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Job2 {

    public void execute(){
        log.info("{} 执行了...", Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}
