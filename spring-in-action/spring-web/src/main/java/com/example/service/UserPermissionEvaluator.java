package com.example.service;

import com.example.domain.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.service
 * @Description:
 * 自定义校验方法
 * @date 2022/3/5 下午8:37
 */
public class UserPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object target, Object perssion) {

        if(target instanceof User){
            System.out.println("自定义认证");
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {

        throw new UnsupportedOperationException();
    }
}
