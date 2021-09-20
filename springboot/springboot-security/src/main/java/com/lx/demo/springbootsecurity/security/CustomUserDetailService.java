package com.lx.demo.springbootsecurity.security;

import com.lx.demo.springbootsecurity.domain.Authority;
import com.lx.demo.springbootsecurity.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
//@Component("userDetailService")
public class CustomUserDetailService implements UserDetailsService {

    /**
     * 数据库中的用户信息
     */
    private List<User> users = new ArrayList<>();

    {
        final List<Authority> authorityList = Arrays.asList(new Authority("ROLE_ADMIN"), new Authority("ROLE_USER"));
        for (int i = 0; i < 3; i++) {
            User user = new User();
            String userName = "admin_"+i;
            if(i==2){
                // 所有权限
                user.setAuthorities(new HashSet(authorityList));
            }else{
                user.setAuthorities(new HashSet(Collections.singletonList(authorityList.get(i))));
            }
            user.setId(0L);
            user.setName(userName);
            user.setAge(0);
            user.setPassword("11");
            user.setActivated(true);
            users.add(user);
        }

    }

    /**
     * 在这里可以硬灌用户来支持访问, 与{@see SecurityCOnfiguration中inMemoryAuthentication效果相同}
     * 每次的校验逻辑都会通过这里, 可以增加用户后读取数据提供动态校验
     * {@see https://docs.spring.io/spring-boot/docs/2.0.8.RELEASE/reference/htmlsingle/#howto-change-the-user-details-service-and-add-user-accounts}
     *
     * 这里设置好的用户名密码，　security内部会进行比对
     * {@see org.springframework.security.authentication.dao.DaoAuthenticationProvider#retrieveUser(java.lang.String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)}
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = new User();
//        String userName = "admin";
//        user.setAuthorities(new HashSet(Arrays.asList(new Authority("ADMIN1"), new Authority("USER"))));
//        user.setId(0L);
//        user.setName(userName);
//        user.setAge(0);
//        user.setPassword("11");
//        user.setActivated(true);
        for (User user : users) {
            if(username.equals(user.getName())){
                return this.createSpringSecurityUser(username, user);
            }
        }
        return null;
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
