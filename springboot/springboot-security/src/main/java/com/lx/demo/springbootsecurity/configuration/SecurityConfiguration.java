package com.lx.demo.springbootsecurity.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.AllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import javax.servlet.http.HttpServletRequest;

/**
 * 实现安全控制 首先挤成 {@link WebSecurityConfigurerAdapter}
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        return new InMemoryUserDetailsManager(
//                User.withUsername("user").password("password")
//                        .authorities("ROLE_USER").build(),
//                User.withUsername("beans").password("beans")
//                        .authorities("ROLE_BEANS").build(),
//                User.withUsername("admin").password("admin")
//                        .authorities("ROLE_ACTUATOR", "ROLE_USER").build());
//    }

    /**
     * 配置登录用户
     * {@see https://github.com/mercyblitz/segmentfault-lessons/blob/master/spring-boot/lesson-15/spring-boot-lesson-15/src/main/java/com/segmentfault/springbootlesson15/security/WebSecurityConfiguration.java}
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("xiaomage").password("123456").roles("ADMIN")
                .and().withUser("刘德华").password("123456").roles("USER");
    }

    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //CSRF
//        http.csrf().csrfTokenRepository(new CookieCsrfTokenRepository()).requireCsrfProtectionMatcher(
//                httpServletRequest -> httpServletRequest.getMethod().equals("POST")
//        );

        // CSP header
//        http.headers().contentSecurityPolicy("script-src https://code.jquery.com/");

        // X-Frame-Options header
        // 相同域名是允许的
        // http.headers().frameOptions().sameOrigin();

        // 实现白名单方式
//        http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(new AllowFromStrategy() {
//            @Override
//            public String getAllowFromValue(HttpServletRequest request) {
//                return "xiaomage.com";
//            }
//        }));

        // XSS header
//        http.headers().xssProtection().block(false);

        http.authorizeRequests().anyRequest().permitAll();

        // 授权
//        http.authorizeRequests().anyRequest().fullyAuthenticated()
//                .and().
//                formLogin().usernameParameter("name") // 用户名参数
//                .passwordParameter("pwd") // 密码参数
////                .loginProcessingUrl("/loginAction") // 登录 Action 的 URI
//                .loginPage("/login") // 登录页面 URI
//                .failureForwardUrl("/home") // 登录失败后的页面URI
//                .permitAll()
//                .and().logout().permitAll();
    }
}
