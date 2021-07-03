package com.example.config;

import com.example.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.jaxws.JaxWsPortProxyFactoryBean;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class WsServiceConfiguration {

    @Bean
    public JaxWsPortProxyFactoryBean accountService() throws MalformedURLException {
        JaxWsPortProxyFactoryBean jaxProxy  = new JaxWsPortProxyFactoryBean();
        jaxProxy.setWsdlDocumentUrl(new URL("http://localhost:8887/AccountService?wsdl"));
        jaxProxy.setServiceName("AccountService");
        jaxProxy.setServiceInterface(AccountService.class);
        jaxProxy.setPortName("AccountServiceSoap");
        jaxProxy.setNamespaceUri("http://soa.example.com/service");
        return jaxProxy;
    }
}