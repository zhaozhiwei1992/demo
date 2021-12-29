package com.example.springbootresttemplate.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
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
        HttpServletRequest httpRequest = ((HttpServletRequest) servletRequest);
        final BufferedReader reader = httpRequest.getReader();
        final String s = reader.readLine();
        if (!StringUtils.isEmpty(s)){
           log.info("通过inputstream 获取body参数 {} ", s);
        }
        reader.close();
    }

    @Override
    public void destroy() {

    }
}
