package com.example.springbootjpa.service;

import com.example.springbootjpa.domain.User;
import com.example.springbootjpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User save(User user){
        return userRepository.save(user) ;
    }

    public Collection<User> findAll(){
        return userRepository.findAll();
    }
}
