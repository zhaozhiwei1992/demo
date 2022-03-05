package com.example.config;

import com.example.service.UserPermissionEvaluator;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
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
//@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig
//    可以重写方法来设置用户等, web端设置了这里就么必要了
        extends GlobalMethodSecurityConfiguration {

    /**
     * @Description: 自定义方法认证校验
     * 任何地方的表达式中使用hasPermission()来保
     * 护方法，都会调用UserPermissionEvaluator来决定用户是
     * 否有权限调用方法
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        final DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new UserPermissionEvaluator());
        return expressionHandler;
    }
}
