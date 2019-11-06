package com.lx.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Microservice.
 * <p>
 * Properties are configured in the application.yml file.
 *
 * 这里可以做一些自定义属性
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
