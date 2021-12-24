package com.example.nacosexample.config;

import com.example.nacosexample.rule.CustomRouteLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomLoadBancerConfig extends ZoneAvoidanceRule {

    public CustomLoadBancerConfig() {
        super();
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        ILoadBalancer lb = super.getLoadBalancer();
        return new CustomRouteLoadBalancer(lb);
    }

    @Override
    public Server choose(Object key) {
        return super.choose(key);
    }

}
