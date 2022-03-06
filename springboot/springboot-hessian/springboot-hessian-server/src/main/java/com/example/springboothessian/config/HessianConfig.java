package com.example.springboothessian.config;

import com.example.springboothessian.web.hessian.SimpleHessian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

/**
 * @Title: HessianConfig
 * @Package com/example/springboothessian/config/HessianConfig.java
 * @Description: 暴露hessian服务
 * {@see HessianExporter}
 * @author zhaozhiwei
 * @date 2021/10/18 下午7:53
 * @version V1.0
 */
@Configuration
public class HessianConfig {

    @Autowired
    private SimpleHessian simpleHessian;

    /**
     * @data: 2022/3/6-下午6:34
     * @User: zhaozhiwei
     * @method: simpleHesainExporter

     * @return: org.springframework.remoting.caucho.HessianServiceExporter
     * @Description:
     * Bean名称作为访问hessian的一部分
     * com.example.springboothessian.config.HessianConfig#helloClient()
     * factory.setServiceUrl("http://localhost:8080/SimpleHessianExp");
     */
    //	/发布服务
    @Bean("/SimpleHessianExp")
    public HessianServiceExporter simpleHesainExporter() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(simpleHessian);
        exporter.setServiceInterface(SimpleHessian.class);
        return exporter;
    }
}
