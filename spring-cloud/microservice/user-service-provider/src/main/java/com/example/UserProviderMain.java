package com.example;

import com.example.intercepter.CustomIntercepter;
import com.example.stream.InputOutputChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务提供者: 用来对外提供用户服务, 类似平时写的一些service
 * 需要注册到eureka中心
 *
 * @author zhaoz
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableBinding(value = {InputOutputChannel.class})
public class UserProviderMain
        implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(UserProviderMain.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//      拦截请求
        registry.addInterceptor(new CustomIntercepter());
    }

    /**
     * 服务发现时传入参数, eurekaserver认证的另一种方式
     *
     * @return
     */
//    @Bean
//    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs(){
//        final DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs = new DiscoveryClient
//        .DiscoveryClientOptionalArgs();
//        final List<ClientFilter> additionalFilters = new ArrayList<ClientFilter>();
//        additionalFilters.add(new HTTPBasicAuthFilter("user", "password"));
//        discoveryClientOptionalArgs.setAdditionalFilters(additionalFilters);
//        return discoveryClientOptionalArgs;
//    }

//    配套测试 发送接收消息， 注意exchange都是用的output
//    @StreamListener(value = InputOutputChannel.MESSAGEOUTPUT, condition = "headers['type']=='dog'")
//    public void handle(String body) {
//        System.out.println("Received: " + body);
//    }
//
    /**
     * 表示让定义的方法生产消息。
     * <p>
     * 注：用 InboundChannelAdapter 注解的方法上即使有参数也没用。即下面test方法不要有参数。
     * <p>
     * fixedDelay：多少毫秒发送1次
     * maxMessagesPerPoll：一次发送几条消息。
     *
     * @return
     */
//    @Bean
//    @InboundChannelAdapter(value = InputOutputChannel.MESSAGEOUTPUT,
//            poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll = "2"))
//    public MessageSource<String> test() {
//        return () -> {
//            Map<String, Object> map = new HashMap<>(1);
//            map.put("type", "dog");
//            return new GenericMessage<>("abcdef", map);
//        };
//    }
}
