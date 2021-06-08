package com.example.springbootsecurityoauth2.configuration;

import com.example.springbootsecurityoauth2.handler.CustomAuthenticationFailureHandler;
import com.example.springbootsecurityoauth2.handler.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * 实现安全控制 首先挤成 {@link WebSecurityConfigurerAdapter}
 * <p>
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置登录用户
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Value("#{'${auth_whitelist}'.split(',')}")
    private List<String> authWhitelist;

    /**
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        只打开下边这行可以使用授权方式
//        http.formLogin().and().csrf().disable();

        http
//                认证配置
                .authorizeRequests()
                // 验证码等跳过
                .antMatchers(authWhitelist.toArray(new String[0])).permitAll()
                .anyRequest().authenticated()

//                登录表单相关配置
                .and()
                .formLogin()
                .loginPage("/authentication/form")
                .successHandler(customAuthenticationSuccessHandler)
                .permitAll()

                // remember me相关
                .and()
                .rememberMe()
                .userDetailsService(userDetailsService)

//                退处相关配置
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll();


    }

    /**
     * @param web :
     * @data: 2021/6/6-下午4:57
     * @User: zhaozhiwei
     * @method: configure
     * @return: void
     * @Description: 忽略静态文件
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }
}
