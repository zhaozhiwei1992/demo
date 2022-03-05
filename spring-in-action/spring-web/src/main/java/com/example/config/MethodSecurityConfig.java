package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: TODO
 * @date 2022/3/4 下午5:18
 */
@Configuration
//如果securedEnabled属
//        性的值为true的话，将会创建一个切点，这样的话Spring Security切
//        面就会包装带有@Secured注解的方法
@EnableGlobalMethodSecurity(securedEnabled = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig
//    可以重写方法来设置用户等, web端设置了这里就么必要了
//        extends GlobalMethodSecurityConfiguration
{
}
