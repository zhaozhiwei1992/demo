package com.example.config;

import com.example.domain.Cat;
import com.example.domain.SpeakInterface;
import org.springframework.context.annotation.*;

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
 * ComponentScan等价使用Spring context命名空间的<context:component-scan>
 *
 * @date 2022/2/16 上午9:42
 */
//@Configuration
@ComponentScan(basePackageClasses = SpeakInterface.class)
// Import注解可以聚合多个配置
//@Import()
// 通过ImportResource直接引入xml配置，等价<import></import>
//@ImportResource("classpath:spring-context.xml")
public class AnimalsConfig {

    /**
     * @Description: 通过JavaConfig配置bean, 等价 <bean id="cat" class=xxx><bean/>
     */
    @Bean
    public Cat cat(){
        return new Cat();
    }
}
