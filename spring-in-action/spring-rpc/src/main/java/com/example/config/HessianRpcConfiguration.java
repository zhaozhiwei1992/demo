package com.example.config;

import com.example.service.UserService;
import com.example.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Properties;

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
@Configuration
public class HessianRpcConfiguration {

    /**
     * @data: 2022/3/6-下午7:31
     * @User: zhaozhiwei
     * @method: userServiceExporter

     * @return: org.springframework.remoting.caucho.HessianServiceExporter
     * @Description: 描述
     * BeanNameUrlHandlerMapping, 通过bean名暴露url
     * 三月 06, 2022 7:35:45 下午 org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping registerHandler
     * 信息: Mapped URL path [/user.hessian] onto handler '/user.service
     *
     * HessianServiceExporter（稍后会有更详细的介绍）是一个
     * Spring MVC控制器，它接收Hessian请求，并将这些请求转换成对被
     * 导出POJO的方法调用
     *
     * 问题处理:
     * 1. com.caucho.hessian.client.HessianConnectionException: HessianProxy cannot connect
     * 找不到hession暴露服务, 之前同时存在controller输出中有提示BeanNameUrlHandlerMapping暴露出的urlpath, 但是不可用
     * 使用com.example.config.HessianRpcConfiguration#handlerMapping()暴露，
     * 测试可以访问com.example.service.UserServiceHessianTest#testFindOne()
     */
//    @Bean
    @Bean("/user.hessian")
    public HessianServiceExporter userServiceExporter(){
        final HessianServiceExporter hessianServiceExporter = new HessianServiceExporter();
        hessianServiceExporter.setService(new UserServiceImpl());
        hessianServiceExporter.setServiceInterface(UserService.class);
        return hessianServiceExporter;
    }

    @Bean
    public HessianProxyFactoryBean hessianClient(){
        final HessianProxyFactoryBean hessianProxyFactoryBean = new HessianProxyFactoryBean();
        hessianProxyFactoryBean.setServiceUrl("http://localhost:8080/user.hessian");
        hessianProxyFactoryBean.setServiceInterface(UserService.class);
        return hessianProxyFactoryBean;
    }

    /**
     * @data: 2022/3/6-下午6:17
     * @User: zhaozhiwei
     * @method: handlerMapping

     * @return: org.springframework.web.servlet.HandlerMapping
     * @Description: 描述
     * 配置一个URL映射来确保DispatcherServlet把请
     * 求转给hessianServiceExporter
     *
     * SimpleUrlHandlerMapping手动指定handler暴露
     *
     */
//    @Bean
//    public HandlerMapping handlerMapping(){
//        final SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
//        final Properties mappings = new Properties();
////        信息: Mapped URL path [/user.hessian] onto handler 'hessianServiceExporter'
//        mappings.setProperty("/user.hessian", "userServiceExporter");
//        simpleUrlHandlerMapping.setMappings(mappings);
//        return simpleUrlHandlerMapping;
//    }

    @Bean
    public BeanNameUrlHandlerMapping nameUrlHandlerMapping(){
        return new BeanNameUrlHandlerMapping();
    }
}
