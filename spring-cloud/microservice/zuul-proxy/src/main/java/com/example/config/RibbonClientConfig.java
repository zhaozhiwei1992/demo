package com.example.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonClientConfig {

    /**
     * @data: 2024/2/22-下午4:09
     * @User: zhaozhiwei
     * @method: getRule

     * @return: com.netflix.loadbalancer.IRule
     * @Description: 调整默认轮询算法, 查看IRule各种实现即可
     * RoundRobinRule：轮询。
     * RandomRule：随机。
     * WeightedResponseTimeRule：根据响应时间来分配权重的方式，响应的越快，分配的值越大。
     * BestAvailableRule：会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务。
     * RetryRule：先按照轮询策略获取服务，如果获取服务失败则在指定时间内进行重试，获取可用的服务。
     * ZoneAvoidanceRule：根据性能和可用性选择服务。
     * AvailabilityFilteringRule：会先过滤掉由于多次访问故障而处于断路器状态的服务，还有并发的连接数量超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问。
     */
    @Bean
    public IRule getRule() {
        return new RandomRule();
    }
}