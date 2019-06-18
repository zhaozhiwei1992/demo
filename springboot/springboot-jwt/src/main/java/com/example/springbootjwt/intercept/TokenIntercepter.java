package com.example.springbootjwt.intercept;

import com.example.springbootjwt.security.jwt.TokenProvider;
import com.example.springbootjwt.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Component
public class TokenIntercepter extends HandlerInterceptorAdapter {

    private final String ACCESS_TOKEN="access_token";

    private final TokenProvider tokenProvider;

    public TokenIntercepter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        String token= CookieUtil.getCookieValue(request,ACCESS_TOKEN);
        boolean isAjax=CookieUtil.isAjax(request);
        if(StringUtils.isEmpty(token)){
            if(isAjax){
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("{\"code\":\"-1\",\"msg\":\"error\",\"url\":\""+"http://bing.com"+"\"}");
                return false;
            }
            response.sendRedirect("http://bing.com");
            return false;
        }

        if(tokenProvider.validateToken(token)){
            return super.preHandle(request, response, handler);
        }
        if(isAjax){
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("{\"code\":\""+"200"+"\"" +
                    ",\"msg\":\""+"xx"+"\",\"url\":\""+"http://bing.com"+"\"}");
            return false;
        }
        response.sendRedirect("http://bing.com");
        return false;
    }
}
