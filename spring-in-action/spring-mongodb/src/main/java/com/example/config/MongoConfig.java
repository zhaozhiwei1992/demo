package com.example.config;

import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description: TODO
 * @date 2022/3/2 上午9:13
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.example.repository")
public class MongoConfig {

    @Bean
    public MongoProperties mongoProperties(){
//        注入properties配置
        final MongoProperties mongoProperties = new MongoProperties();
//        mongodb://[username:password@]host1[:port1][,...hostN[:portN]][/[defaultauthdb][?options]]
        mongoProperties.setUri("mongodb://admin:123456@192.168.7.6/admin");
//        mongoProperties.setPort(MongoProperties.DEFAULT_PORT);
//        mongoProperties.setUsername("admin");
//        mongoProperties.setPassword("123456".toCharArray());
        return mongoProperties;
    }


    @Bean
    public MongoDbFactory mongoFactory(MongoProperties mongoProperties) throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClientURI(mongoProperties().getUri()));
//        return new SimpleMongoDbFactory(new MongoClientURI(mongoProperties().getUri()));
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoFactory(mongoProperties()));
    }

    public class MongoProperties {
        public static final int DEFAULT_PORT = 27017;
        public static final String DEFAULT_URI = "mongodb://localhost/test";
        private String host;
        private Integer port = null;
        private String uri;
        private String database;
        private String authenticationDatabase;
        private String gridFsDatabase;
        private String username;
        private char[] password;
        private Class<?> fieldNamingStrategy;

        public MongoProperties() {
        }

        public String getHost() {
            return this.host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getDatabase() {
            return this.database;
        }

        public void setDatabase(String database) {
            this.database = database;
        }

        public String getAuthenticationDatabase() {
            return this.authenticationDatabase;
        }

        public void setAuthenticationDatabase(String authenticationDatabase) {
            this.authenticationDatabase = authenticationDatabase;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public char[] getPassword() {
            return this.password;
        }

        public void setPassword(char[] password) {
            this.password = password;
        }

        public Class<?> getFieldNamingStrategy() {
            return this.fieldNamingStrategy;
        }

        public void setFieldNamingStrategy(Class<?> fieldNamingStrategy) {
            this.fieldNamingStrategy = fieldNamingStrategy;
        }

        public String getUri() {
            return this.uri;
        }

        public String determineUri() {
            return this.uri != null ? this.uri : "mongodb://localhost/test";
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Integer getPort() {
            return this.port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getGridFsDatabase() {
            return this.gridFsDatabase;
        }

        public void setGridFsDatabase(String gridFsDatabase) {
            this.gridFsDatabase = gridFsDatabase;
        }

        public String getMongoClientDatabase() {
            return this.database != null ? this.database : (new MongoClientURI(this.determineUri())).getDatabase();
        }
    }

}
