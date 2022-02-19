package com.example.config;

import com.example.domain.CommonBean;
import com.example.domain.DevBean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description:
 * 测试spring作用域
 * 单例（Singleton）：在整个应用中，只创建bean的一个实例。
 * 原型（Prototype）：每次注入或者通过Spring应用上下文获取的时候，都会创建一个新的bean实例。
 * 会话（Session）：在Web应用中，为每个会话创建一个bean实例。
 * 请求（Rquest）：在Web应用中，为每个请求创建一个bean实例。
 * @date 2022/2/17 上午11:43
 */
@Configuration
@ComponentScan(basePackageClasses = CommonBean.class)
@ImportResource(locations = "classpath:scope-context.xml")
public class ScopeConfig {

    @Bean
    public DevBean singletonBean(){
        return new DevBean();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DevBean prototypeBean(){
        return new DevBean();
    }

    /**
     * @data: 2022/2/17-下午5:53
     * @User: zhaozhiwei
     * @method: sessionBean

     * @return: com.example.domain.DevBean
     * @Description: 描述
     * <aop:scoped-proxy>是与@Scope注解的proxyMode属性功能相
     * 同的Spring XML配置元素。它会告诉Spring为bean创建一个作用域代
     * 理。默认情况下，它会使用CGLib创建目标类的代理。但是我们也可
     * 以将proxy-target-class属性设置为false，进而要求它生成基
     * 于接口的代理：
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.INTERFACES)
    public DevBean sessionBean(){
        return new DevBean();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
    public DevBean requestBean(){
        return new DevBean();
    }
}