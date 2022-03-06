package com.example.config;

import com.example.service.UserService;
import com.example.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.BurlapProxyFactoryBean;
import org.springframework.remoting.caucho.BurlapServiceExporter;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description:
 * HessianSeriviceExporter实现为一个Spring MVC控制器。这意
 * 味着为了使用导出的Hessian服务, 所以需要以下步骤
 *
 * 在web.xml中配置Spring的DispatcherServlet，并把我们的
 * 应用部署为　Web应用；
 *
 * 在Spring的配置文件中配置一个URL处理器，把Hessian服务的
 * URL分发给对应的Hessian服务bean。
 *
 * 1. 第一步服务端创建配置HessianRpcConfiguration
 * 2. 基于springmvc 创建web配置, 并增加地址映射com.example.config.SpringWebAppInitializer#getServletMappings(), *.hessian
 * 3. 创建代理
 * @date 2022/3/6 下午4:56
 */
//@Configuration
public class BurlapRpcConfiguration {

//    @Bean
    @Bean("/user.burlap")
    public BurlapServiceExporter userServiceExporter(){
        final BurlapServiceExporter burlapServiceExporter = new BurlapServiceExporter();
        burlapServiceExporter.setService(new UserServiceImpl());
        burlapServiceExporter.setServiceInterface(UserService.class);
        return burlapServiceExporter;
    }

    @Bean
    public BurlapProxyFactoryBean burlapClient(){
        final BurlapProxyFactoryBean hessianProxyFactoryBean = new BurlapProxyFactoryBean();
        hessianProxyFactoryBean.setServiceUrl("http://localhost:8080/user.burlap");
        hessianProxyFactoryBean.setServiceInterface(UserService.class);
        return hessianProxyFactoryBean;
    }

    @Bean
    public BeanNameUrlHandlerMapping nameUrlHandlerMapping(){
        return new BeanNameUrlHandlerMapping();
    }
}
