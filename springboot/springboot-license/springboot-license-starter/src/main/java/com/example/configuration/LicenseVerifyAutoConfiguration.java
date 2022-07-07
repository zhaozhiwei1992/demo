package com.example.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: LicenseVerifyAutoConfiguration
 * @Package com/example/configuration/LicenseVerifyAutoConfiguration.java
 * @Description: 自动装配, 及扫描包
 * @author zhaozhiwei
 * @date 2022/7/7 下午4:33
 * @version V1.0
 */
@Configuration
@ComponentScan(value = "com.example")
public class LicenseVerifyAutoConfiguration {

}
