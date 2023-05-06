package com.example.springbootjpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: 一些需要注册的hutool配置
 * 1. 使用Hutool封装了Spring中Bean获取的工具类——SpringUtil。
 * @date 2022/5/8 上午11:29
 */
@Configuration
@Import(cn.hutool.extra.spring.SpringUtil.class)
public class HutoolConfiguration {}
