package com.lx.demo.springbootel.service;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

/**
 *
注入配置文件需使用@PropertySource指定文件地址，若使用@Value注入，
 则要配置一个PropertySourcesPlaceholderConfigurer的Bean。注意@Value("${book.name}"),使用的是$而不是#。
注入 Properties 还可以从 Environment 中获得。
*/
@Service
@PropertySource("classpath:test.properties")
public class DemoElService {

    /**
     *
     //注入普通字符串
     */
    @Value("I LOVE YOU!")
    private String normal;

    /**
     * //注入操作系统属性
     */
    @Value("#{systemProperties['os.name']}")
    private String osName;

    /**
     * //注入表达式结果
     */
    @Value("#{T(java.lang.Math).random() * 100.0}")
    private String randomNumber;

    // 自定义计算类测试
    @Value("#{T(com.lx.demo.springbootel.service.rule.Math).sum(1.0,20,30,1.11)}")
    private BigDecimal sumNum;

    /**
     * //注入其他Bean属性
     */
    @Value("#{demoService.another}")
    private String fromAnother;

    /**
     * //注入文件资源
     */
    @Value("classpath:/test.txt")
    private Resource testFile;

    /**
     * //注入网址资源
     */
    @Value("http://www.baidu.com")
    private Resource testUrl;

    /**
     * // 注入配置文件
     */
    @Value("${book.name}")
    private String bookName;

    @Autowired // 注入配置文件
    private Environment environment;

    @Bean // 注入配置文件
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    public void outputResource(){
        System.out.println("合计: " + sumNum);
        try {
            System.out.println(normal);
            System.out.println(osName);
            System.out.println(randomNumber);
            System.out.println(fromAnother);
            System.out.println(IOUtils.toString(testFile.getInputStream()));
            System.out.println(IOUtils.toString(testUrl.getInputStream()));
            System.out.println(bookName);
            System.out.println(environment.getProperty("book.author"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}