package com.example.springcloudconsulclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DiscoveryClientController {

    private final DiscoveryClient discoveryClient;

    private final String currentApplicationName;

    @Autowired
    public DiscoveryClientController(DiscoveryClient discoveryClient,
                                     @Value("${spring.application.name}") String currentApplicationName) {
        this.discoveryClient = discoveryClient;
        this.currentApplicationName = currentApplicationName;
    }

    /**
     * 获取当前实例，实例名称如果一样就苦逼了
     * @return
     */
    @GetMapping("/discovery/currentinstance")
    public ServiceInstance getCurrentServerInstance(){
        return discoveryClient.getInstances(currentApplicationName).get(0);
    }

    /**
     * 获取所有服务名称
     * @return
     */
    @GetMapping("/discovery/services")
    public List<String> getServices(){
        return discoveryClient.getServices();
    }

    /**
     * 获取所有实例信息
     * @return
     */
    @GetMapping("/discovery/serviceinstances")
    public List<ServiceInstance> getServiceInstances(){
        List<String> services = this.getServices();
        ArrayList<ServiceInstance> serviceInstances = new ArrayList<>();
        services.forEach(serviceName->{
            serviceInstances.addAll(discoveryClient.getInstances(serviceName));
        });
        return serviceInstances;
    }

}
