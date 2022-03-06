package com.example.config;

import com.example.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;
import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class WsServiceConfiguration {

    @Bean
    public SimpleJaxWsServiceExporter jaxWsExporter() {
        SimpleJaxWsServiceExporter exporter = new SimpleJaxWsServiceExporter();
//      web环境下使用时, 端口不能与业务端口冲突
        exporter.setBaseAddress("http://localhost:8887/");
        System.out.println("webservice started on port(s): 8887 (ws)");
        System.out.println("webservice测试请求地址示例: (用chrome访问) http://localhost:8887/UserService?WSDL");
        return exporter;
    }

    @Bean
    public JaxWsPortProxyFactoryBean userServiceClient() throws MalformedURLException {
        JaxWsPortProxyFactoryBean jaxProxy  = new JaxWsPortProxyFactoryBean();
        jaxProxy.setWsdlDocumentUrl(new URL("http://localhost:8887/UserService?wsdl"));
        jaxProxy.setServiceName("UserService");
        jaxProxy.setServiceInterface(UserService.class);
        jaxProxy.setPortName("UserServiceSoap");
        jaxProxy.setNamespaceUri("http://soa.example.com/service");
        return jaxProxy;
    }
}