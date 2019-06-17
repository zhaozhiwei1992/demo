package com.lx.demo.springbootsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * rest接口同样登录以后才能访问
 */
@RestController
@RequestMapping(value = "/rest")
public class ResourceController {
    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

    @GetMapping("/hello")
    public String hello(Authentication authentication){
        log.info("resource: user {}", authentication);
        return "hello:"+ authentication.getName();
    }

    @GetMapping("/sayhello")
    public String sayhello(Authentication authentication){
        log.info("resource: user {}", authentication);
        return "sayhello:"+ authentication.getName();
    }

    /**
     * @EnableGlobalMethodSecurity(prePostEnabled = true) 需要打开
     * @param authentication
     * @return
     */
    @GetMapping("/sayhello1")
    @PreAuthorize("hasRole('ADMIN')")
    public String sayhello1(Authentication authentication){
        log.info("resource: user {}", authentication);
        return "sayhello:"+ authentication.getName();
    }
}

