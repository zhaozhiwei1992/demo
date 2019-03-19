package com.lx.demo.restonspringmvc.config;

import com.lx.demo.restonspringmvc.http.converter.PropertiesPerson2HttpMessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
/**
*org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#addDefaultHttpMessageConverters
*/
@Configuration
public class PersonalMvcConfig implements WebMvcConfigurer {
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //mvc内部加载的一些转换器
        System.out.println("所有的解析方式" + converters);
        //可以通过下面方式只留下自己需要的转换器
//        converters.clear();
        //只留下json解析
//        converters.add(new MappingJackson2HttpMessageConverter());
        converters.clear();
        converters.add(new PropertiesPerson2HttpMessageConverter());
    }
}
