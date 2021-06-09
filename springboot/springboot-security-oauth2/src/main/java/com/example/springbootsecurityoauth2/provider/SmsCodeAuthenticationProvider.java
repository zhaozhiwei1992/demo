package com.example.springbootsecurityoauth2.provider;

import com.example.springbootsecurityoauth2.domain.SmsCodeAuthenticationToken;
import com.example.springbootsecurityoauth2.handler.CustomAuthenticationFailureHandler;
import com.github.benmanes.caffeine.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

/**
 * @Title: SmsCodeAuthenticationProvider
 * @Package com/example/springbootsecurityoauth2/provider/SmsCodeAuthenticationProvider.java
 * @Description:
 * com.example.springbootsecurityoauth2.filter.SmsCodeAuthenticationFilter#attemptAuthentication(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 * |
 * {@link ProviderManager#authenticate(org.springframework.security.core.Authentication)}
 * 通过上述方式走自定义provider校验
 *
 * @author zhaozhiwei
 * @date 2021/6/9 上午9:58
 * @version V1.0
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(SmsCodeAuthenticationProvider.class);

    private UserDetailsService userDetailsService;

    Cache<String, Object> caffeineCache;

    /**
     * @data: 2021/6/9-上午9:36
     * @User: zhaozhiwei
     * @method: authenticate
      * @param authentication :
     * @return: org.springframework.security.core.Authentication
     * @Description: authentication明细信息会在com.example.springbootsecurityoauth2.filter.SmsCodeAuthenticationFilter#setDetails(javax.servlet.http.HttpServletRequest, com.example.springbootsecurityoauth2.domain.SmsCodeAuthenticationToken)
     * 写入，后续认证可以获取
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());

        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        // 在这里校验验证码是否正确，验证码一般存放到redis中
        // 获取到缓存中的smsCode,和参数中的进行比对
        String key = "code:sms:001";
        final String smsCode = (String) caffeineCache.getIfPresent(key);
        final Object o = authenticationToken.getDetails();
        if(o instanceof Map){
            Map detailMap = (Map) o;
            // 认证失败
            if(!smsCode.equals(detailMap.get("smsCode"))){
                logger.error("Authentication failed: smscoe does not match stored value");
//                异常会被com.example.springbootsecurityoauth2.handler.CustomAuthenticationFailureHandler捕获
                throw new BadCredentialsException("smscode 验证失败");
            }
        }

        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public Cache<String, Object> getCaffeineCache() {
        return caffeineCache;
    }

    public void setCaffeineCache(Cache<String, Object> caffeineCache) {
        this.caffeineCache = caffeineCache;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}