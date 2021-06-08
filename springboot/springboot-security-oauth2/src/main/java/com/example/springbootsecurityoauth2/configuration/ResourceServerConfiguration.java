package com.example.springbootsecurityoauth2.configuration;

import com.example.springbootsecurityoauth2.handler.CustomAuthenticationFailureHandler;
import com.example.springbootsecurityoauth2.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.util.List;

/**
 * @Title: ResourceServerConfiguration
 * @Package com/example/springbootsecurityoauth2/configuration/ResourceServerConfiguration.java
 * @Description:
 *     资源服务器@EnableResourceServer用于保护接口，验证token，如果token有效就可以访问api
 *     认证服务器@EnableAuthorizationServer用于获取token
 *     资源服务器可以单独配置在要保护的项目上，也可以与认证服务器配置在同一个项目中
 * @author zhaozhiwei
 * @date 2021/6/8 下午3:54
 * @version V1.0
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Value("#{'${auth_whitelist}'.split(',')}")
    private List<String> authWhitelist;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/authentication/form")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler);

        http.authorizeRequests()
                .antMatchers(authWhitelist.toArray(new String[0])).permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}