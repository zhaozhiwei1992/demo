package com.lx.demo.springbootsecurity.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title: CustomExpiredSessionStrategy
 * @Package com/lx/demo/springbootsecurity/handler/CustomExpiredSessionStrategy.java
 * @Description: 自定义session超时策略, 如果不阻止其它用户登录情况下，前面用户刷新页面就会看到 下述提示
 * @author zhaozhiwei
 * @date 2021/6/5 下午10:13
 * @version V1.0
 */
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {

    private static final Logger logger = LoggerFactory.getLogger(CustomExpiredSessionStrategy.class);

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("其他人登录了.");

        logger.error("其他人登录了.");
    }
}