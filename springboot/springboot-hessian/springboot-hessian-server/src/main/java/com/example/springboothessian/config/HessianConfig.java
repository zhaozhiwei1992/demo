package com.example.springboothessian.config;

import com.example.springboothessian.web.hessian.SimpleHessian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
public class HessianConfig {

    @Autowired
    private SimpleHessian simpleHessian;

    //	/发布服务
    @Bean("/SimpleHessianExp")
    public HessianServiceExporter simpleHesainExporter() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(simpleHessian);
        exporter.setServiceInterface(SimpleHessian.class);
        return exporter;
    }
}
