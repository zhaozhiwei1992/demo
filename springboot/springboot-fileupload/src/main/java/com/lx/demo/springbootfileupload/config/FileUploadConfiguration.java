package com.lx.demo.springbootfileupload.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @Title: FileUploadConfiguration
 * @Package com/lx/demo/springbootfileupload/config/FileUploadConfiguration.java
 * @Description:
 * SpringBoot文件上传异常之提示The temporary upload location xxx is not valid
 * https://www.cnblogs.com/yihuihui/p/10372887.html
 * @author zhaozhiwei
 * @date 2021/6/24 下午7:57
 * @version V1.0
 */
@Configuration
public class FileUploadConfiguration {

    @Value("${server.tomcat.basedir:/tmp/tomcat}")
    private String tmpLocation;

    /**
     * @data: 2021/6/24-下午8:04
     * @User: zhaozhiwei
     * @method: multipartConfigElement

     * @return: javax.servlet.MultipartConfigElement
     * @Description:
     * #指定multipartFile临时文件存放路径，但是内置的tomcat创建的临时文件夹路径还是不变的
     * spring.servlet.multipart.location=临时文件路径
     * #指定springboot启动时内置tomcat创建的临时文件夹的路径
     * #server.tomcat.basedir=/data/ops/app/cms/cache
     * 原文链接：https://blog.csdn.net/weixin_43791937/article/details/106199607
     * https://www.cnblogs.com/yihuihui/p/10372887.html
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize(DataSize.parse("128KB")); //KB,MB

        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("256KB"));

        //Sets the directory location where files will be stored.
        File tmpFile = new File(tmpLocation);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(tmpLocation);
//        factory.setLocation("/tmp");
        return factory.createMultipartConfig();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
