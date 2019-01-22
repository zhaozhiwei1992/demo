package com.example.springbootprofile.controller;

import com.example.springbootprofile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile/{name}")
    public String getProfileMsg(@PathVariable String name){
        return profileService.getProfileConfig();
    }
}
