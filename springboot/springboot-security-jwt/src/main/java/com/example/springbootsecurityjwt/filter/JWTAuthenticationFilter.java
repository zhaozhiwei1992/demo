package com.example.springbootsecurityjwt.filter;

import com.alibaba.fastjson.JSON;
import com.example.springbootsecurityjwt.configuration.SpringSecurityConfig;
import com.example.springbootsecurityjwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * token的校验
 * 该类继承自BasicAuthenticationFilter，在doFilterInternal方法中，
 * 从http头的Authorization 项读取token数据，然后用Jwts包提供的方法校验token的合法性。
 * 如果校验通过，就认为这是一个取得授权的合法请求
 *
 * @author xxm
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = request.getRequestURI();
        String header = request.getHeader(JwtUtil.AUTHORIZATION);

        Map map = new HashMap();
        //跳过不需要验证的路径
        if (Arrays.asList(SpringSecurityConfig.AUTH_WHITELIST).contains(url)) {
            chain.doFilter(request, response);
            return;
        }

        // 满足表达式的跳过
        if (antPathMatcher.match("/actuator/**", url)) {
            chain.doFilter(request, response);
            return;
        }

        if (StringUtils.isBlank(header) || !header.startsWith(JwtUtil.TOKEN_PREFIX)) {

            map.put("codeCheck", false);
            map.put("msg", "Token为空");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(map));
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            //json.put("status", "-2");
            map.put("codeCheck", false);
            map.put("msg", "Token已过期");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(map));
            logger.error("Token已过期: {} " + e);
        } catch (UnsupportedJwtException e) {
            //json.put("status", "-3");
            map.put("codeCheck", false);
            map.put("msg", "Token格式错误");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(map));
            logger.error("Token格式错误: {} " + e);
        } catch (MalformedJwtException e) {
            //json.put("status", "-4");
            map.put("codeCheck", false);
            map.put("msg", "Token没有被正确构造");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(map));
            logger.error("Token没有被正确构造: {} " + e);
        } catch (SignatureException e) {
            //json.put("status", "-5");
            map.put("codeCheck", false);
            map.put("msg", "Token签名失败");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(map));
            logger.error("签名失败: {} " + e);
        } catch (IllegalArgumentException e) {
            //json.put("status", "-6");
            map.put("codeCheck", false);
            map.put("msg", "Token非法参数异常");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(map));
            logger.error("非法参数异常: {} " + e);
        } catch (Exception e) {
            //json.put("status", "-9");
            map.put("codeCheck", false);
            map.put("msg", "Invalid Token");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(map));
            logger.error("Invalid Token " + e.getMessage());
        }
    }

    /**
     * Extract the OAuth bearer token from a header.
     *
     * @param request The request.
     * @return The token, or null if no OAuth authorization header was supplied.
     */
    protected String extractHeaderToken(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(JwtUtil.AUTHORIZATION);
        // typically there is only one (most servers enforce that)
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(JwtUtil.TOKEN_PREFIX.toLowerCase()))) {
                String authHeaderValue = value.substring(JwtUtil.TOKEN_PREFIX.length()).trim();
                if ("null".equals(authHeaderValue)) {
                    return null;
                }
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }

        return null;
    }

    public final static String TOKEN = "token";

    protected String getCookie(HttpServletRequest request, String cookieName) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        if (cookie == null) {
            return null;
        }
        String value = cookie.getValue();
        if (!org.springframework.util.StringUtils.hasLength(value)) {
            return null;
        }
        return value;
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,
                                                                  HttpServletResponse response) {
        // 直接获取token方式
//        String token = request.getHeader(JwtUtil.AUTHORIZATION);

        String token = extractHeaderToken(request);

        if (token == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Token not found in headers. Trying request parameters."
                        + request.getRequestURI() + "?" + request.getQueryString());
            }
            token = getCookie(request, TOKEN);
        }

        if (token != null) {
            String userName = "";

            try {
                // 解密Token
                userName = JwtUtil.validateToken(token);
                if (StringUtils.isNotBlank(userName)) {

                    retrieveUser(userName);

                    final UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userName, null, new ArrayList<>());

                    // 通过security一个请求线程中带一些特殊信息, 通过request带也可以
                    final Object details = authenticationToken.getDetails();
                    if(Objects.isNull(details)){
                        final Map extensionMap = new HashMap();
                        WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);
                        if (webAuthenticationDetails.getRemoteAddress() != null) {
                            extensionMap.put("remoteAddress", webAuthenticationDetails.getRemoteAddress());
                        }
                        if (webAuthenticationDetails.getSessionId() != null) {
                            extensionMap.put("sessionId", webAuthenticationDetails.getSessionId());
                        }

                        extensionMap.put("tokenid", token);
                        authenticationToken.setDetails(extensionMap);
                    }

                    return authenticationToken;

                }
            } //throw new TokenException("Token格式错误");
            //throw new TokenException("Token没有被正确构造");
            //throw new TokenException("签名失败");
            //throw new TokenException("非法参数异常");
            catch (Exception e) {
                throw e;
                //throw new IllegalStateException("Invalid Token. "+e.getMessage());
                //throw new TokenException("Token已过期");
            }
            return null;
        }
        return null;
    }

    private UserDetailsService userDetailsService;

    protected final UserDetails retrieveUser(String username) throws AuthenticationException {
        UserDetails loadedUser;
        try {
            // 调用loadUserByUsername时加入type前缀
            loadedUser = this.userDetailsService.loadUserByUsername(/* authentication.getType() + "&:@" + */ username);
        } catch (UsernameNotFoundException var6) {
            throw var6;
        } catch (Exception var7) {
            throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException("用户[" + username + "]不存在！");
        } else {
            return loadedUser;
        }
    }

}
