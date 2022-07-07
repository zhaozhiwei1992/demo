package com.example.configuration;

import com.example.aspect.LicenseAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LicenseAspectConfig {
    @Bean
    public LicenseAspect getLicenseAspect(){
        return new LicenseAspect();
    }

}
