package com.example.springbootprofile.domain;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Externalized Configuration
 * Spring Boot lets you externalize your configuration so that you can work with the same application code in different environments.
 * You can use properties files, YAML files, environment variables, and command-line arguments to externalize configuration.
 * Property values can be injected directly into your beans by using the @Value annotation,
 * accessed through Spring’s Environment abstraction, or be bound to structured objects through @ConfigurationProperties.
 */
@ConfigurationProperties(prefix = "zhao")
public class InitWithProperties {

    private Long id;

    private String name;

    private int age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "InitWithProperties{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
