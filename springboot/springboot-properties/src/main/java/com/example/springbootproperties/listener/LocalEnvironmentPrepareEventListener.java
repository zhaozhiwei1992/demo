package com.example.springbootproperties.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.Map;

/**
 * 了解Spring的加载流程，我们得知Spring在上下文准备完毕(配置信息解析完毕并创建好了应用上下文)
 * 之后，会调用推送一个ApplicationEnvironmentPreparedEvent，从这个事件中，我们可以获取到ConfigurableEnvironment对象，而ConfigurableEnvironment
 * 对象可以获取到MutablePropertySources。对于MutablePropertySources，它包含了Spring的所有 的配置信息，包括我们启动应用的application.properties文件的配置信息。
 *
 */
public class LocalEnvironmentPrepareEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        MutablePropertySources propertySources = event.getEnvironment().getPropertySources();
        for (PropertySource<?> propertySource : propertySources) {
            boolean applicationConfig = propertySource.getName().contains("applicationConfig");
            if (!applicationConfig) {
               continue;
            }
//            Object property = propertySource.getProperty("msg");
            Map<String, OriginTrackedValue> source = (Map<String, OriginTrackedValue>) propertySource.getSource();
            OriginTrackedValue originTrackedValue = source.get("msg");
            OriginTrackedValue newOriginTrackedValue = OriginTrackedValue.of("updateBy_com.example.springbootproperties.listener.LocalEnvironmentPrepareEventListener", originTrackedValue.getOrigin());
//            unmodifymap, 改个锤子
//ComparisonPropertySource
            source.put("msg", newOriginTrackedValue);
//            System.out.println(property);
        }
    }
}