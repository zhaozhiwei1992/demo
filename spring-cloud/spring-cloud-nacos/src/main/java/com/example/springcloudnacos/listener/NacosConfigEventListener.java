package com.example.springcloudnacos.listener;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

@Component
public class NacosConfigEventListener {

    private static final Logger logger = LoggerFactory.getLogger(NacosConfigEventListener.class);

    @Value("${spring.application.name}")
    private String appName;

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Autowired
    private NacosConfigProperties configProperties;

    @PostConstruct
    public void registerListener() throws NacosException {
        nacosConfigManager.getConfigService().addListener(appName + ".yml", configProperties.getGroup(),
                new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String configInfo) {
                        handleConfigChangeEvent(configInfo);
                    }
                });
    }

    private void handleConfigChangeEvent(String configInfo) {
        logger.info("配置信息变更后: {}", configInfo);
    }
}