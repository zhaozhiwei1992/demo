package com.example.springbootprofile.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile(value = "prod")
public class ProdProfileService implements ProfileService{
    @Override
    public String getProfileConfig() {
        return "I am prod";
    }
}
