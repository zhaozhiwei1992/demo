package com.lx.demo.exceptiononspringmvc;

import com.lx.demo.exceptiononspringmvc.dto.BussinessException;
import com.lx.demo.exceptiononspringmvc.dto.ResultVO;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

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
 *
 * order 数字小优先加载, 如果使用了order 就可以优先于RestControllerAdvice精确异常
 * 统一异常处理的一种方案
 * 原springxml项目可以直接<bean id="exceptionResolver" class="com.lx.demo.exceptiononspringmvc.CustomHandlerExceptionResolver"/>
 */
@Component
@Slf4j
//@Order(-1000)
public class CustomHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception ex) {
        Method method = null;
        if (handler != null && handler instanceof HandlerMethod) {
            method = ((HandlerMethod) handler).getMethod();
        }

        ResultVO result = new ResultVO();
        StringBuilder sb = new StringBuilder();
        //处理异常
        if(ex instanceof BussinessException) {
            resolverBussinessException(ex, sb, result);
        } else if (ex instanceof BindException) {
            resolverBindException(ex, sb, result);
        } else {
            resolverOtherException(ex, sb, result);
        }

        result.setCode("000000");
        result.setResult(sb.toString());
        result.setTime(new Date());

        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            httpServletResponse.getWriter().write(result.toString());
        } catch (IOException e) {
            log.error("与客户端通讯异常：" + e.getMessage(), e);
            e.printStackTrace();
        }

        log.debug("异常：" + ex.getMessage(), ex);
        ex.printStackTrace();

        log.error("[{}] system error", method, ex);
        return new ModelAndView();
    }

    /*
     * 处理业务层异常
     */
    private void resolverBussinessException(Exception ex, StringBuilder sb, ResultVO result) {
        BussinessException businessException = (BussinessException) ex;
        sb.append(businessException.getMsg());
        addResult(result, "业务异常");
    }

    /*
     * 处理参数绑定异常
     */
    private void resolverBindException(Exception ex, StringBuilder sb, ResultVO result) {
        BindException be = (BindException) ex;
        List<FieldError> errorList = be.getBindingResult().getFieldErrors();
        for (FieldError error : errorList) {
            sb.append(error.getObjectName());
            sb.append("对象的");
            sb.append(error.getField());
            sb.append("字段");
            sb.append(error.getDefaultMessage());
        }
        addResult(result, "参数传递异常");
    }

    /*
     * 处理其他异常
     */
    private void resolverOtherException(Exception ex, StringBuilder sb, ResultVO result) {
        sb.append(ex.getMessage());
        addResult(result, "其他异常");
    }

    /*
     * 封装code和msg
     */
    private void addResult(ResultVO result, String msg) {
        result.setMsg(msg);
    }
}
