package com.example.config;

import com.netflix.loadbalancer.NoOpPing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 需要放行的URL
     */
    private static String[] AUTH_WHITELIST = {
            // -- register url
            "/busportal/login.page",
            "/busportal/login/loginId.rcp",
            "/error/**",
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/actuator/**",
            "/404.jsp",
            "/403.jsp",
            "/500.jsp"
            // other public endpoints of your API may be appended to this array
    };

    @Value("${security.ignoring.antpatterns}")
    public String[] setAuthWhitelist(String antpatterns){
        if(StringUtils.hasLength(antpatterns)) {
            antpatterns += ","+StringUtils.arrayToCommaDelimitedString(AUTH_WHITELIST);
            AUTH_WHITELIST = StringUtils.commaDelimitedListToStringArray(antpatterns);
        }
        return AUTH_WHITELIST;
    }


    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http basic认证
        http.authorizeRequests()
                .and().authorizeRequests()
                .antMatchers("/management/**").permitAll()
                // swagger start
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()
                //扩展白名单
                .antMatchers(AUTH_WHITELIST).permitAll()
                //全部校验
                .anyRequest().authenticated()
                .and()
                .csrf().disable() //关闭CSRF
                .httpBasic();
    }

    /**
     * 明文密码测试
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    private CustomUserDetailService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailService).passwordEncoder(this.passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 配置忽略验证的url
        web.ignoring().antMatchers("/users/**", "swagger-ui.html");
    }

    @Component
    class CustomUserDetailService implements UserDetailsService{

        /**
         * 这里可以模拟账号，可以设计自己的权限用户
         * @param username
         * @return
         * @throws UsernameNotFoundException
         */
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            if("user".equals(username)){
                return new SecurityUser("user", "password1", "role1");
            }else if("admin".equals(username)){
                return new SecurityUser("admin", "password2", "role2");
            }else{
                return null;
            }
        }
    }


    /**
     * 安全校验用户实体
     */
    class SecurityUser implements UserDetails{

        private static final long serialVersionUID = 7177310759961750302L;

        private Long id;
        private String username;
        private String password;
        private String role;

        public SecurityUser(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }

        public SecurityUser() {
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            final ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            final SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(this.role);
            authorities.add(grantedAuthority);
            return authorities;
        }

        //重写以后　这些方法该返回返回，　返回参数全部改为true
        @Override
        public String getPassword() {
            return this.password;
        }

        @Override
        public String getUsername() {
            return this.username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
