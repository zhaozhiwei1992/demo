package com.lx.demo.springbootsecurity.security;

import com.lx.demo.springbootsecurity.domain.Authority;
import com.lx.demo.springbootsecurity.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailService")
public class DomainUserDetailService implements UserDetailsService {

    /**
     * 在这里可以硬灌用户来支持访问, 与{@see SecurityCOnfiguration中inMemoryAuthentication效果相同}
     * 每次的校验逻辑都会通过这里, 可以增加用户后读取数据提供动态校验
     * {@see https://docs.spring.io/spring-boot/docs/2.0.8.RELEASE/reference/htmlsingle/#howto-change-the-user-details-service-and-add-user-accounts}
     *
     * 这里设置好的用户名密码，　security内部会进行比对
     * @param s
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = new User();
        String userName = "admin";
        user.setAuthorities(new HashSet(Arrays.asList(new Authority("ADMIN"), new Authority("USER"))));
        user.setId(0L);
        user.setName(userName);
        user.setAge(0);
        user.setPassword("11");
        user.setActivated(true);
        return this.createSpringSecurityUser(userName, user);
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(),
                grantedAuthorities);
    }
}
