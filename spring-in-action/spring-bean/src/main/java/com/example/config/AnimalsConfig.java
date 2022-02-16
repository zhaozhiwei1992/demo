package com.example.config;

import com.example.domain.SpeakInterface;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description:
 * spring中，肯定有bean初始化扫描加载的入口, 或者是xml, 或者是javabean
 * 通过该类扫描加载类
 * 注: 通过org.springframework.context.annotation.ComponentScan#basePackageClasses()可以将class作为基类，
 * 可以扫描当前类所在文件夹及子文件夹类
 *
 * @date 2022/2/16 上午9:42
 */
@Configuration
@ComponentScan(basePackageClasses = SpeakInterface.class)
public class AnimalsConfig {
}
