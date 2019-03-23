package com.example.springbootprofile.controller;

import com.example.springbootprofile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile/{name}")
    public String getProfileMsg(@PathVariable String name){
        return profileService.getProfileConfig();
    }

    /**
     * 获取application-{profile}.properties中配置的属性
     * @param id
     * @param name
     * @return
     */
    @GetMapping("/getproperties")
    public String getPropertiesMsg(@Value("${person.id}") String id, @Value("${person.name: 默认值}") String name){
        return Arrays.asList(id, name).toString();
    }
}
