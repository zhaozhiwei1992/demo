package com.lx.demo.springbootsecurity.service;

import com.lx.demo.springbootsecurity.service.RbacService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Component("rbacServiceImpl")
public class RbacServiceImpl implements RbacService {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        boolean hasPermission = false;

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            // TODO 根据用户名去加载数据库中的用户对应的权限
            Set<String> urls = new HashSet<>();
            // 注意, 白名单不能被拦截
            urls.add("/login");
            if("admin_0".equals(username)){
                urls.add("/rest/sayhello2");
            }

            for (String url : urls) {
                if (antPathMatcher.match(url, request.getRequestURI())) {
                    hasPermission = true;
                    break;
                }
            }
        }

        return hasPermission;
    }
}