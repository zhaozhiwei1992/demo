package com.lx.demo.springbootbstrap.configuration;

import com.lx.demo.springbootbstrap.domain.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 *
 * @link https://docs.spring.io/spring-boot/docs/2.0.8.RELEASE/reference/htmlsingle/#boot-features-developing-auto-configuration
 org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
 com.lx.demo.springbootbstrap.configuration.PersonAutoConfiguration
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "person", name = "enabled", havingValue = "true")
public class PersonAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "zhangsan")
    public Person person(){
        return new Person();
    }
}
