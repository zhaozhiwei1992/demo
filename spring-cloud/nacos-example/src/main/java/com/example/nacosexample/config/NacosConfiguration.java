package com.example.nacosexample.config;

import com.alibaba.cloud.nacos.ConditionalOnNacosDiscoveryEnabled;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConditionalOnNacosDiscoveryEnabled
public class NacosConfiguration {

    @Value("${server.port}")
    private Integer serverPort;

    @Bean
    public NacosDiscoveryProperties nacosProperties() {
        NacosDiscoveryProperties nacosDiscoveryProperties = new NacosDiscoveryProperties();
        Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
//        给服务打标记, 简单先使用port
        metadata.put("serviceprovince", serverPort + "");
        return nacosDiscoveryProperties;
    }
}
