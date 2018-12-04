package com.lx.web.intercept;

import com.lx.common.annotation.Anoymous;
import com.lx.common.constants.WebConstantRegistry;
import com.lx.common.utils.CookieUtil;
import com.lx.user.IUserCoreService;
import com.lx.user.dto.CheckAuthRequest;
import com.lx.user.dto.CheckAuthResponse;
import com.lx.web.controller.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class TokenIntercepter extends HandlerInterceptorAdapter {

    private final String ACCESS_TOKEN="access_token";

    @Autowired
    IUserCoreService iUserCoreService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Object bean=handlerMethod.getBean();

        if(isAnoymous(handlerMethod)){
            return true;
        }
        if(!(bean instanceof BaseController)){
            throw new RuntimeException("must extend basecontroller");
        }
        String token= CookieUtil.getCookieValue(request,ACCESS_TOKEN);
        boolean isAjax=CookieUtil.isAjax(request);
        if(StringUtils.isEmpty(token)){
            if(isAjax){
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("{\"code\":\"-1\",\"msg\":\"error\"}");
                return false;
            }
            response.sendRedirect(WebConstantRegistry.MALL_SSO_ACCESS_URL);
            return false;
        }
        CheckAuthRequest checkAuthRequest=new CheckAuthRequest();
        checkAuthRequest.setToken(token);
        CheckAuthResponse checkAuthResponse=iUserCoreService.validToken(checkAuthRequest);
        if("000000".equals(checkAuthResponse.getCode())){
            BaseController baseController=(BaseController)bean;
            baseController.setUid(checkAuthResponse.getUid());
            return super.preHandle(request, response, handler);
        }
        if(isAjax){
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("{\"code\":\""+checkAuthResponse.getCode()+"\"" +
                    ",\"msg\":\""+checkAuthResponse.getMsg()+"\"}");
            return false;
        }
        response.sendRedirect(WebConstantRegistry.MALL_SSO_ACCESS_URL);
        return false;
    }

    private boolean isAnoymous(HandlerMethod handlerMethod){
        Object bean=handlerMethod.getBean();
        Class clazz=bean.getClass();
        if(clazz.getAnnotation(Anoymous.class)!=null){
            return true;
        }
        Method method=handlerMethod.getMethod();
        return method.getAnnotation(Anoymous.class)!=null;
    }
}
