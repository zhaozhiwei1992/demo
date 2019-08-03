package com.example.config;

import com.example.filter.PreRequestLogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public PreRequestLogFilter preRequestLogFilter(){
        return new PreRequestLogFilter();
    }
}
