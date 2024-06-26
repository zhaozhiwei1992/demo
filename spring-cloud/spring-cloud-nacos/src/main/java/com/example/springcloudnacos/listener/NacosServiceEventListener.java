package com.example.springcloudnacos.listener;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class NacosServiceEventListener implements EventListener {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceEventListener.class);

    @Value("${spring.application.name}")
    private String appName;

    //    @Autowired
//    @NacosInjected
    // 根本注入不了，坑
    private NamingService namingService;

    @Autowired
    private NacosServiceManager nacosServiceManager;

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @PostConstruct
    public void registerListener() throws NacosException {
        // 应用启动后，注册事件监听器
        namingService = nacosServiceManager.getNamingService(nacosDiscoveryProperties.getNacosProperties());
        namingService.subscribe(appName, this);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof NamingEvent) {
            NamingEvent namingEvent = (NamingEvent) event;
            // 处理服务变更事件
            handleServiceChangeEvent(namingEvent);
        }
    }

    private void handleServiceChangeEvent(NamingEvent event) {
        // 根据事件类型更新服务列表或执行其他逻辑
        logger.info("服务信息 {}", ((NamingEvent) event).getServiceName());
        NamingEvent namingEvent = (NamingEvent) event;
        // 获取变更的服务实例列表
        List<Instance> instances = namingEvent.getInstances();

        logger.info("实例信息: {}", instances);
    }
}