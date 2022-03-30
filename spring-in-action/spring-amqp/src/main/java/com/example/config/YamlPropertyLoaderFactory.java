package com.example.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Properties;

/**
 * @Title: YamlPropertyLoaderFactory
 * @Package com/example/config/YamlPropertyLoaderFactory.java
 * @Description:
 * spring4 加载yaml 中配置
 * https://stackoverflow.com/questions/21271468/spring-propertysource-using-yaml
 * https://blog.csdn.net/niugang0920/article/details/115611553
 * @author zhaozhiwei
 * @date 2022/3/29 下午4:45
 * @version V1.0
 */
public class YamlPropertyLoaderFactory extends DefaultPropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if (resource == null){
            return super.createPropertySource(name, null);
        }

        final YamlPropertiesFactoryBean factoryBean = new YamlPropertiesFactoryBean();
        factoryBean.setResources(resource.getResource());

        final Properties properties = factoryBean.getObject();
        return new PropertiesPropertySource(resource.getResource().getFilename(), properties);
    }
}
