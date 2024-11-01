package com.lx.demo.springbootsecurity.service;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Title: UserPermissionEvaluator
 * @Package com/lx/demo/springbootsecurity/service/UserPermissionEvaluator.java
 * @Description: 按钮权限
 * @author zhaozhiwei
 * @date 2022/7/18 上午11:15
 * @version V1.0
 */
public class MethodPermissionEvaluator implements PermissionEvaluator {

    /**
     * hasPermission鉴权方法
     * @Param  permission 请求按钮的权限
     * @Return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if(principal != null && principal instanceof UserDetails){
            String name=((UserDetails) principal).getUsername();
            // 获取用户角色
            // 获取角色对应的权限信息 permissions
            final ArrayList<String> permissions = new ArrayList<>();
            // 模块:菜单:按钮
            permissions.add("admin:user:add");

            if (permissions.contains(permission.toString())){
                return true;
            }
        }
        return hasPermission;
    }
    
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}