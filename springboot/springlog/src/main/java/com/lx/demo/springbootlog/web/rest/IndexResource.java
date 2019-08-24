package com.lx.demo.springbootlog.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexResource {
    private static final Logger logger = LoggerFactory.getLogger(IndexResource.class);

    @GetMapping("/")
    public String index(){
        logger.debug("{}", Thread.currentThread().getStackTrace()[1].getMethodName());
        return "xx";
    }
}
