package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.swing.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description:
 *
 * @EnableWebSecurity注解将会启用Web安全功能。但
 * 它本身并没有什么用处，Spring Security必须配置在一个实现了
 * WebSecurityConfigurer的bean中，或者（简单起见）扩
 * 展WebSecurityConfigurerAdapter
 *
 *
 * @date 2022/2/26 下午11:39
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @Description: 描述
     * 通过重载，配置user-detail服务, 扩展入口很多， 常用见下面四点
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//      1.  使用内存用户, 测试使用
        auth.inMemoryAuthentication()
                .withUser("admin").password("admin").roles("ADMIN", "USER")
                .and()
//                roles()方法是authorities()方法的简写形
//式。roles()方法所给定的值都会添加一个“ROLE_”前缀，并将其作为权限授予给用户
//                .withUser("user").password("user").authorities("ROLE_USER");
                .withUser("user").password("user").roles("USER");

//      2.  使用数据库配置 jdbc
//        默认的数据源最少配置能够让一切运转起来，但是它对我们的数据库模式有一些要求。它预期存在某些存储用户数据的表
//        auth.jdbcAuthentication().dataSource(dataSource);

//        auth.jdbcAuthentication().dataSource(dataSource)
//        覆盖security 默认查询方法
//                .usersByUsernameQuery("select username, password, true from t_users where username = ?")
//                .authoritiesByUsernameQuery("select username, 'ROLE_USER from t_users where username = ?'")
//        Spring Security的加密模块包括了三个这样的实
//        现：BCryptPasswordEncoder、NoOpPasswordEncoder和
//        StandardPasswordEncoder。
//                .passwordEncoder(new StandardPasswordEncoder(""));

//      3. 实现UserDetailService接口.loadByUserName, 自定义用户查找方法
//        auth.userDetailsService(userDetailsService());

//      4.  扩展认证provider
//        auth.authenticationProvider(customLoginAuthenticationProvider());
//        auth.eraseCredentials(false);
    }

    /**
     * @Description: 描述
     * 通过重载，配置Spring Security的Filter链
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * @Description: 描述
     * 通过重载，配置如何通过拦截器保护请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        默认会全部拦截, 具体可以看super中实现
//        super.configure(http);

        http

//        formLogin()方法启用了security自带基本的登录页功能
                .formLogin()
//                自定义登录页面
//                .loginPage()
                .and()

//        HTTP Basic认证（HTTP Basic Authentication）会直接通过HTTP请求本身
//                对外提供接口之类的会用到
//                .httpBasic()

//        如果使用Spring的表单绑定标签的话，<sf:form>标
//        签会自动为我们添加隐藏的CSRF token标签, thymeleaf表单默认也会有token标签

//        可以查看security默认login页面内容如下
//         <input name="_csrf" type="hidden" value="e278e29a-b102-45a1-9848-8f5d67eb69e8" />

//                .csrf().disable()
                .authorizeRequests()
//                ROLE_USER才可以访问/users
                .antMatchers("/users").hasAuthority("ROLE_USER")
                .antMatchers("/homePage").hasAuthority("ROLE_USER")
                .anyRequest().permitAll()
        ;

//      spring默认实现了一些基于SpringEL的校验表达式, 也可以自己扩展各种表达式, 通过access方法校验
//                .anyRequest().access("@rbacServiceImpl.hasPermission(request, authentication)")

    }
}
