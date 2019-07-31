package com.example.web.rest;

import com.example.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 通过resttemplate方式获取用户信息
 */
@RestController
@Slf4j
@RequestMapping("/resttemplate")
public class UserResourceByRestTemplate {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service.provider.name}")
    private String providerInstanceName;

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    /**
     * 查找所有用户
     * resttemplate请求会负载均衡, 原理类似下面choose，挑了一个
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/users")
    public List<User> getAllUser() throws InterruptedException {
        return restTemplate.getForObject("http://"+providerInstanceName+"/users", List.class);
    }

    /**
     * 启动多个userprovider实例, 测试负载均衡
     * 测试:
     * seq 5 |xargs -i echo "http://localhost:8080/resttemplate/log-user-instance" |xargs -n 1 curl -X GET
     * 输出:
     * 2019-07-31 23:52:51.593  INFO 13529 --- [nio-8080-exec-4] c.e.web.rest.UserResourceByRestTemplate  : 当前被宠信的节点: user-service-provider:192.168.71.64:9090
     * 2019-07-31 23:52:51.605  INFO 13529 --- [nio-8080-exec-6] c.e.web.rest.UserResourceByRestTemplate  : 当前被宠信的节点: user-service-provider:192.168.71.64:9091
     * 2019-07-31 23:52:51.617  INFO 13529 --- [nio-8080-exec-7] c.e.web.rest.UserResourceByRestTemplate  : 当前被宠信的节点: user-service-provider:192.168.71.64:9092
     * 2019-07-31 23:52:51.626  INFO 13529 --- [nio-8080-exec-9] c.e.web.rest.UserResourceByRestTemplate  : 当前被宠信的节点: user-service-provider:192.168.71.64:9090
     * 2019-07-31 23:52:51.638  INFO 13529 --- [io-8080-exec-10] c.e.web.rest.UserResourceByRestTemplate  : 当前被宠信的节点: user-service-provider:192.168.71.64:9091
     */
    @GetMapping("/log-user-instance")
    public void logUserInstance(){
        final ServiceInstance serviceInstance = loadBalancerClient.choose(providerInstanceName);
        log.info("当前被宠信的节点: {}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(), serviceInstance.getPort());
    }
}
