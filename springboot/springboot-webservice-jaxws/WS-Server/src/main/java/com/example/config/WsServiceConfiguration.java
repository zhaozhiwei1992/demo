package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;

/**
 * @Title: WsServiceConfiguration
 * @Package com/example/config/WsServiceConfiguration.java
 * @Description: TODO 大佬写点东西
 * SimpleJaxWsServiceExporter：SimpleJaxWsServiceExporter将Spring管理的Bean发布为JAX-WS运行时的服务端点，与其他服务导出器（如RMIServiceExporter
 * ）有点不同，不需要为SimpleJaxWsServiceExporter指定要导出的Bean的引用，它会将使用JAX-WS注解所标注（如注解@WebService
 * 、@WebMethod等）的所有bean发布为JAX-WS服务。本文主要介绍SimpleJaxWsServiceExporter来发布服务。
 * JaxWsPortProxyFactoryBean：JaxWsPortProxyFactoryBean是一个Spring的工厂bean，它能生成一个知道如何与远程Web
 * 服务交互的代理，这些代理实现了服务接口，代理可以被注入到其他的bean中，从而远程服务调用就类似本地调用一样简单。
 *
 * + https://blog.csdn.net/lili13897741554/article/details/90241866
 * @author zhaozhiwei
 * @date 2021/6/30 上午9:50
 * @version V1.0
 */
@Configuration
public class WsServiceConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(WsServiceConfiguration.class);

    @Bean
    public SimpleJaxWsServiceExporter jaxWsExporter() {
        SimpleJaxWsServiceExporter exporter = new SimpleJaxWsServiceExporter();
//         端口不能与业务端口冲突
        exporter.setBaseAddress("http://localhost:8887/");
        logger.info("webservice started on port(s): 8887 (ws)");
        logger.info("webservice测试请求地址: http://localhost:8887/AccountService?WSDL");
        return exporter;
    }
}