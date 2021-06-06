package com.lx.demo.springbootsecurity.configuration;

import com.lx.demo.springbootsecurity.filter.ImageValidateCodeFilter;
import com.lx.demo.springbootsecurity.handler.CustomAuthenticationFailureHandler;
import com.lx.demo.springbootsecurity.handler.CustomAuthenticationSuccessHandler;
import com.lx.demo.springbootsecurity.handler.CustomExpiredSessionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.AllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 * 实现安全控制 首先挤成 {@link WebSecurityConfigurerAdapter}
 * <p>
 * springboot 2.1.3密码必须有加密策略，否则报错,这里是俩种处理方式,
 * 一种采用spring官方demo中的方式，InMemoryUserDetailsManager
 * 另一种初始化一个passwordencoder
 * {@see https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-actuator
 * -custom-security/src/main/java/sample/actuator/customsecurity/SecurityConfiguration.java}
 * {@see https://docs.spring.io/spring-boot/docs/2.0.8.RELEASE/reference/htmlsingle/#boot-features-security-mvc}
 * org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
 * <p>
 * 下面的方式是spring默认初始化方式，可以重写
 * org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
 * org.springframework.boot.autoconfigure.security.SecurityProperties.User 这个里面会默认初始化user， 密码为随机数
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//
//        return new InMemoryUserDetailsManager(
//                User.withDefaultPasswordEncoder().username("zhangsan").password("11")
//                        .authorities("ROLE_USER").build(),
//                User.withDefaultPasswordEncoder().username("lisi").password("11")
//                        .authorities("ROLE_ADMIN").build(),
//                User.withDefaultPasswordEncoder().username("admin").password("admin")
//                        .authorities("ROLE_ACTUATOR", "ROLE_USER").build());
//    }

    /**
     * https://www.harinathk.com/spring/no-passwordencoder-mapped-id-null/
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 配置登录用户
     * {@see https://github.com/mercyblitz/segmentfault-lessons/blob/master/spring-boot/lesson-15/spring-boot-lesson
     * -15/src/main/java/com/segmentfault/springbootlesson15/security/WebSecurityConfiguration.java}
     *
     * @param auth
     * @throws Exception
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.inMemoryAuthentication()
////                .withUser("zhangsan").password("11").roles("ADMIN")
////                .and()
////                .withUser("lisi").password("11").roles("USER");
//        auth
//            .inMemoryAuthentication()
//                .withUser("admin").password("11").roles("USER");
//    }


    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
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
//        http.headers().frameOptions().sameOrigin();

        // 实现白名单方式
//        http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(new AllowFromStrategy() {
//            @Override
//            public String getAllowFromValue(HttpServletRequest request) {
//                return "xiaomage.com";
//            }
//        }));

        // XSS header
//        http.headers().xssProtection().block(false);

//        http.authorizeRequests().anyRequest().permitAll();


        // 授权
//        http.authorizeRequests().anyRequest().fullyAuthenticated()
//                .and()
//                .formLogin()
//                .usernameParameter("name") // 用户名参数
//                .passwordParameter("pwd") // 密码参数
//                .loginProcessingUrl("/loginAction") // 登录 Action 的 URI
//                .loginPage("/login") // 登录页面 URI
//                .failureForwardUrl("/error") // 登录失败后的页面URI
//                .permitAll()
//                .and().logout().permitAll();
        final ImageValidateCodeFilter imageValidateCodeFilter = new ImageValidateCodeFilter();
        imageValidateCodeFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);

        http
//                认证配置
                .authorizeRequests()
                // 验证码等跳过
                .antMatchers("/", "/home", "/hello", "/code/image").permitAll()
                .anyRequest().authenticated()
                .and()

//                登录表单相关配置
                .addFilterBefore(imageValidateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .permitAll()

                // remember me相关
                .and()
                .rememberMe()
                .userDetailsService(userDetailsService)
//                令牌仓库
                .tokenRepository(persistentTokenRepository())
//        tokenValiditySeconds token保存时间，单位秒
                .tokenValiditySeconds(60 * 60 * 60 * 30)

//                退处相关配置
                .and()
                .logout()
                .permitAll();

//        会话设置
        http.sessionManagement()
                // 会话超时到hello页面
                .invalidSessionUrl("/login")
//               一个用户只能一个会话
                .maximumSessions(1)
                .expiredSessionStrategy(new CustomExpiredSessionStrategy())
//                当达到maximumSessions设置的最大会话个数时阻止登录
//                阻止后默认会一直提示用户密码错误
                .maxSessionsPreventsLogin(true);
    }

    @Autowired
    private DataSource dataSource;

    /**
     * @data: 2021/6/6-下午9:18
     * @User: zhaozhiwei
     * @method: persistentTokenRepository
     * @return: org.springframework.security.web.authentication.rememberme.PersistentTokenRepository
     * @Description: remember me
     * {@see org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl#initDao()}
     *
     * 持久化用户登录的信息。
     * create table persistent_logins (
     *   username  varchar(64) not null,
     *   series    varchar(64) primary key,
     *   token     varchar(64) not null,
     *   last_used timestamp   not null
     * );
     *
     *  首次response会写入到客户端cookie, 下次请求request会带有remember-me信息
     * Set-Cookie
     * 	remember-me=NVdGQzNGJTJCaVRhbU5RJTJCazZJQ3JLaHclM0QlM0Q6TmtGVzIwZmxxeGZpbjJjUiUyRkE1R1V3JTNEJTNE;
     * 	Max-Age=6480000; Expires=Fri, 20-Aug-2021 13:23:30 GMT; Path=/; HttpOnly
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);

        return tokenRepository;
    }

    /**
     * @data: 2021/6/6-下午4:57
     * @User: zhaozhiwei
     * @method: configure
      * @param web :
     * @return: void
     * @Description: 忽略静态文件
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }
}
