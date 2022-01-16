package com.example.springbootproperties.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootproperties.config
 * @Description: TODO
 * @date 2022/1/16 下午8:07
 */
@Data
@Log4j2
@Configuration
@ConfigurationProperties("name")
public class ValueConfiguration {

    private String name;

    @Value("${name}")
    public void setName(String name) {
        log.info("赋值前: {}", name);
        this.name = name;
        log.info("赋值后: {}", name);
    }
}
