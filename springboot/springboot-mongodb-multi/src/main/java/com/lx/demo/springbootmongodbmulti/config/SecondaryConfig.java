package com.lx.demo.springbootmongodbmulti.config;

import com.mongodb.MongoClientURI;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * 可以参考mybatis多数据源
 * 1. properties
 * 2. template
 * 3. factory
 * mybatis多了一个datasource
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.lx.demo.springbootmongodbmulti.repository.secondary"
        , mongoTemplateRef = "secondaryTemplate")
public class SecondaryConfig {

    @Bean
    @ConfigurationProperties(prefix="spring.data.mongodb.secondary")
    public MongoProperties secondaryProperties(){
        return new MongoProperties();
    }

    @Bean
    public MongoDbFactory secondaryFactory(MongoProperties secondaryProperties){
        return new SimpleMongoDbFactory(new MongoClientURI(secondaryProperties().getUri()));
    }

    @Bean
    public MongoTemplate secondaryTemplate(){
        return new MongoTemplate(secondaryFactory(secondaryProperties()));
    }
}
