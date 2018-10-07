package com.lx.demo.exceptiononspringmvc.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
public class DefaultHandlerInterceptor implements HandlerInterceptor {

    /**
     * 拦截器：HandlerInterceptor 可以理解 Handler 到底是什么
     *
     * 			处理顺序：preHandle(true) -> Handler: HandlerMethod 执行(Method#invoke) -> postHandle -> afterCompletion
     * 					  preHandle(false)
     * @param request
     * @param response
     * @param handler  这里的handler不一定是handlermethod
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.printf("handler object : %s \n",handler.toString());
        return true;
    }
}
