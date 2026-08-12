package com.acme.shop.user.service;

import com.acme.shop.user.api.UserApi;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserApi {

    @Override
    public String currentUser() {
        return "demo-user";
    }
}
