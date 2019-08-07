package com.lx.demo.springbootpandect.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class CustomFilter implements Filter {

    /**
     * 初始化bean 时初始化参数 {@link com.lx.demo.springbootpandect.config.CustomConfig}
     * 2019-08-07 20:02:21.573  INFO 20938 --- [  restartedMain] c.l.d.s.filter.CustomFilter              : 获取过滤器初始化参数: zhangsan
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("获取过滤器初始化参数: " + filterConfig.getInitParameter("username"));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final StackTraceElement element = Thread.currentThread().getStackTrace()[1];
        log.info("自定义过滤器: " + element.getClassName() + "#" + element.getMethodName());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
