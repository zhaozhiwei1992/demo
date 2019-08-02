package com.example;

import com.example.intercepter.CustomIntercepter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * 用户服务提供者: 用来对外提供用户服务, 类似平时写的一些service
 *  需要注册到eureka中心
 * @author zhaoz
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class UserProviderMain
        extends WebMvcConfigurerAdapter {
    public static void main( String[] args ) {
        SpringApplication.run(UserProviderMain.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomIntercepter());
    }

/**
     * 服务发现时传入参数, eurekaserver认证的另一种方式
     * @return
     */
//    @Bean
//    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs(){
//        final DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs = new DiscoveryClient.DiscoveryClientOptionalArgs();
//        final List<ClientFilter> additionalFilters = new ArrayList<ClientFilter>();
//        additionalFilters.add(new HTTPBasicAuthFilter("user", "password"));
//        discoveryClientOptionalArgs.setAdditionalFilters(additionalFilters);
//        return discoveryClientOptionalArgs;
//    }
}
