package com.acme.shop.user.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String currentUser() {
        return "demo-user";
    }
}
