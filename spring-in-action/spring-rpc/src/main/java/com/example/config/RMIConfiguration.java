package com.example.config;

import com.example.service.UserService;
import com.example.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: 配置RMI Exporter
 * @date 2022/3/6 下午3:56
 */
@Configuration
public class RMIConfiguration {

    private static final String RMI_SERVICENAME = "UserService";
    /**
     * @data: 2022/3/6-下午4:07
     * @User: zhaozhiwei
     * @method: rmiServiceExporter
     * @return: org.springframework.remoting.rmi.RmiServiceExporter
     * @Description:
     * 通过Exporter暴露服务
     */
    @Bean
    public RmiServiceExporter rmiServiceExporter() {
        final RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
//        实际这里最好是注入， 这里只是为了体现spring exporter暴露了UserService
        rmiServiceExporter.setService(new UserServiceImpl());
//        serviceName属性命名了RMI服务
        rmiServiceExporter.setServiceName(RMI_SERVICENAME);
//      serviceInterface属性,指定了此服务所实现的接口。
        rmiServiceExporter.setServiceInterface(UserService.class);

//        rmiServiceExporter.setRegistryHost("rmi.com.example");
//        默认1099
//        rmiServiceExporter.setRegistryPort(8081);
        return rmiServiceExporter;
    }

    /**
     * @data: 2022/3/6-下午4:12
     * @User: zhaozhiwei
     * @method: rmiClient

     * @return: org.springframework.remoting.rmi.RmiProxyFactoryBean
     * @Description:
     * 通过该bean 代理访问暴露的Rmi服务
     */
    @Bean
    public RmiProxyFactoryBean rmiClient(){
        final RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost/" + RMI_SERVICENAME);
        rmiProxyFactoryBean.setServiceInterface(UserService.class);
        return rmiProxyFactoryBean;
    }
}
