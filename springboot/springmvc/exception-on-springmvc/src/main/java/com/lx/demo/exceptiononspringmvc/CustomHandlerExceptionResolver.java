package com.lx.demo.exceptiononspringmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * {@see https://frapples.github.io/articles/2018-09-01-ecbc.html}
 * 全局异常处理器：
 *
 * 实现HandlerExceptionResolver接口。
 * 将实现类作为Spring Bean，这样Spring就能扫描到它并作为全局异常处理器加载。
 * 在resolveException中实现异常处理逻辑。
 * 从参数上，可以看到，不仅能够拿到发生异常的函数和异常对象，还能够拿到HttpServletResponse对象，从而控制本次请求返回给前端的行为。
 *
 * 此外，函数还可以返回一个ModelAndView对象，表示渲染一个视图，比方说错误页面。
 * 不过，在前后端分离为主流架构的今天，这个很少用了。如果函数返回的视图为空，则表示不需要视图。
 *  请求
 * curl -X GET http://127.0.0.1:8080/npe
 * 结果:
 * handler object : public java.lang.String com.lx.demo.exceptiononspringmvc.IndexController.npe()
 * 2019-07-24 23:20:08.271 ERROR 12128 --- [nio-8080-exec-1] c.l.d.e.CustomHandlerExceptionResolver   : [public java.lang.String com.lx.demo.exceptiononspringmvc.IndexController.npe()] system error
 * 注意, 这里 HandlerExceptionResolver和@RestControllerAdvice 同时存在, 只走 RestControllerAdvice中的精确匹配
 *
 * 这里可以判断异常类型实现handler精确匹配的效果，但是不优雅
 */
@Component
@Slf4j
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        Method method = null;
        if (handler != null && handler instanceof HandlerMethod) {
            method = ((HandlerMethod) handler).getMethod();
        }
        log.error("[{}] system error", method, e);
        return new ModelAndView();
    }
}
