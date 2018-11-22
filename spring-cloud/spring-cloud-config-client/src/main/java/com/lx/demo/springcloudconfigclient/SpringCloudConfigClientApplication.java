package com.lx.demo.springcloudconfigclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class SpringCloudConfigClientApplication {


    public static void main(String[] args) {
		SpringApplication.run(SpringCloudConfigClientApplication.class, args);
	}

    /**
     * 扩展实现配置参数最高优先级
     * 该类server.port的参数优先级最高，因为order最优先
     *
     * http://127.0.0.1:8080/actuator/env/
     */
    @Configuration
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static class CustomPropertySourceLocator implements PropertySourceLocator {

        @Override
        public PropertySource<?> locate(Environment environment) {
            Map<String, Object> source = new HashMap<>();
            source.put("server.port","8080");
            MapPropertySource propertySource =
                    new MapPropertySource("mycustom-property-source", source);
            return propertySource;
        }
    }

    private final ContextRefresher contextRefresher;
    private final Environment environment;

    @Autowired
    public SpringCloudConfigClientApplication(ContextRefresher contextRefresher, Environment environment) {
        this.contextRefresher = contextRefresher;
        this.environment = environment;
    }

    /**
     * 定时刷新缓存
     *  五秒执行一次， 延时3秒
     */
    @Scheduled(fixedRate = 5 * 1000, initialDelay = 3 * 1000)
    public void autoRefresh(){
        Set<String> refreshSets = contextRefresher.refresh();
        refreshSets.forEach(propertyName->{
            System.err.printf("[Thread: %s] 当前配置已更新, key: %s, value: %s\n", Thread.currentThread().getName(), propertyName, environment.getProperty(propertyName));
        });
    }
}
