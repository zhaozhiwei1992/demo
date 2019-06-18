package com.example.springbootjwt.intercept;

import com.example.springbootjwt.security.jwt.TokenProvider;
import com.example.springbootjwt.utils.CookieUtil;
import io.jsonwebtoken.Claims;
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
        String token = request.getHeader("id_token");
        Claims claims = tokenProvider.phaseToken(token);
        String id = claims.getId();
        if(tokenProvider.validateToken(token)){
            System.out.println("验证通过, token: " + token);
            return super.preHandle(request, response, handler);
        }
//        response.sendRedirect("http://bing.com");
        return false;
    }
}
