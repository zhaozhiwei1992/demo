package com.lx.demo.springbootsecurity.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title: CustomLogoutSuccessHandler
 * @Package com/lx/demo/springbootsecurity/handler/CustomLogoutSuccessHandler.java
 * @Description:
 *     使当前session失效
 *     清楚与当前用户相关的remember-me记录
 *     清空当前的SecurityContext
 *     重定向到登录页
 * @author zhaozhiwei
 * @date 2021/6/6 下午10:45
 * @version V1.0
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("退出成功");

        response.sendRedirect("/signOut");
    }
}