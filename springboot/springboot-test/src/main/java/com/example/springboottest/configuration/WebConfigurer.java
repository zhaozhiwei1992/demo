package com.example.springboottest.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Arrays;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory>, WebMvcConfigurer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;

    public WebConfigurer(Environment env) {
        this.env = env;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }

        log.info("Web application fully configured");
    }

    /**
     * Customize the Servlet engine: Mime types, the document root, the cache.
     */
    @Override
    public void customize(WebServerFactory server) {
        // When running in an IDE or with ./mvnw spring-boot:run, set location of the static web assets.
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
        return restTemplate;
    }

}
