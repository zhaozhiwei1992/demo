package com.example.config;

import com.example.domain.ProdBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: TODO
 * @date 2022/2/17 上午9:26
 */
@Configuration
@ComponentScan(basePackages = "com.example.domain")
public class ProfileBeanConfig {

    /**
     * @data: 2022/2/17-上午9:32
     * @User: zhaozhiwei
     * @method: prodBean

     * @return: com.example.domain.ProdBean
     * @Description:
     * profile可以跟@Bean结合使用
     */
    @Profile("prod")
    @Bean
    public ProdBean prodBean(){
        return new ProdBean();
    }
}
