package com.example.service;

import com.example.domain.User;

public interface AlertService {

    void sendUserAlert(final User user);

    User receiveUserAlert();
}
