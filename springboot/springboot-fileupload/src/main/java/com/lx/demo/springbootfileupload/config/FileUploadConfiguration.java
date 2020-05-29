package com.lx.demo.springbootfileupload.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfiguration {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize("128KB"); //KB,MB

        /// 设置总上传数据总大小
        factory.setMaxRequestSize("256KB");

        //Sets the directory location where files will be stored.
        // 不起作用
        factory.setLocation("/tmp");
        return factory.createMultipartConfig();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
