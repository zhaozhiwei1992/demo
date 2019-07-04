package com.lx.demo.cxf;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CxfConfig {


    private Bus bus;

    private ISayHello iSayHello;

    public CxfConfig(Bus bus, ISayHello iSayHello) {
        this.bus = bus;
        this.iSayHello = iSayHello;
    }

    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/cxf/*");// 发布服务名称 localhost:8080/cxf
    }

    /**
     * 这里必须使用jaxws,否则没有endpointimpl两参数实现
     *
     <dependency>
     <groupId>org.apache.cxf</groupId>
     <artifactId>cxf-spring-boot-starter-jaxws</artifactId>
     </dependency>
     * org.apache.cxf.endpoint.ServerImpl       : Setting the server's publish address to be /hello
     *
     * wsdl获取方式 curl http://127.0.0.1:8080/cxf/hello\?wsdl
     * @return
     */
    @Bean
    public Endpoint endpoint() {
        // 网上这种方式有问题
        EndpointImpl endpoint = new EndpointImpl(bus, iSayHello);
        endpoint.publish("/hello");
        return endpoint;

        //这种方式　需要占用新的端口, 否则会与当前服务冲突
//        InetAddress address = null;
//        try {
//            address = InetAddress.getLocalHost();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        String uri = "http://" + address.getHostAddress() + ":" + port;
//        return Endpoint.publish(uri + "/hello", iSayHello);
    }
}
