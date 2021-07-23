package com.lx.demo.springbootconditional.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package gov.mof.fasp2.pay.config
 * @Description: TODO
 * @date 2021/7/16 下午4:42
 */
@Configuration
public class ZZDingClientConfig {

    @Value("${ifmis.remote.zzding.access-key:theone01-xxxx}")
    private String accessKey;

    @Value("${ifmis.remote.zzding.secretKey:xxxx}")
    private String secretKey;

    @Bean(initMethod = "init", destroyMethod = "destroy")
    @ConditionalOnProperty(prefix = "remote.rest.zzding", name = "enabled", havingValue = "true")
    public ZZDingOpenAPIClient zzDingOpenAPIClient(){
        return new ZZDingOpenAPIClient(accessKey, secretKey);
    }
}
