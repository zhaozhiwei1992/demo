//package com.lx.demo.readinglist.security;
//
//import com.lx.demo.readinglist.dao.ReaderRepository;
//import com.lx.demo.readinglist.domain.Reader;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.List;
//
///**
// *
// compile("org.springframework.boot:spring-boot-starter-security")
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private ReaderRepository readerRepository;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                //要求登录角色有reader
//                .antMatchers("/").access("hasRole('READER')")
//                .antMatchers("/**").permitAll()
//                .and()
//                .formLogin()
//                //设置登录表单的路径
//                .loginPage("/login")
//                .failureUrl("/login?error=true");
//    }
//
//    @Override
//    protected void configure(
//            AuthenticationManagerBuilder auth) throws Exception {
//        //自定义userdetailservice
//        auth.userDetailsService(new UserDetailsService() {
//                    @Override
//                    public UserDetails loadUserByUsername(String username)
//                            throws UsernameNotFoundException {
////                        List<Reader> users = readerRepository.findByUsername(username);
////                        if (users.size() < 1) {
////                            throw new UsernameNotFoundException(username);
////                        }
////                        return users.get(0);
//                        return new Reader("zhangsan", "zhangsan", "123456");
//                    }
//                });
//    }
//}
