package com.lx.demo.springbootsecurity.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface RbacService {
    /** 判断用户有没有权限访问该请求 */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}