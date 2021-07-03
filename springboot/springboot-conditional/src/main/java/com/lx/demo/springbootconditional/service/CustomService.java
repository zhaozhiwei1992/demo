package com.lx.demo.springbootconditional.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "remote.rest.remoteinfo", name = "enabled", havingValue = "true")
public class CustomService {

    private static final Logger logger = LoggerFactory.getLogger(CustomService.class);

    public CustomService() {
        logger.info("customService init!");
    }
}
