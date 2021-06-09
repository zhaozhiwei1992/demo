package com.example.springbootsecurityoauth2.provider;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: JwtTokenEnhancer
 * @Package com/example/springbootsecurityoauth2/provider/JwtTokenEnhancer.java
 * @Description: Token增强器
 * @author zhaozhiwei
 * @date 2021/6/9 下午7:58
 * @version V1.0
 */
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        // 在jwt中添加自定义字段
        Map<String, Object> info = new HashMap<>();
        info.put("foo", "bar");

        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}