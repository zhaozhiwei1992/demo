package com.example.springbootjpa.domain.listener;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

/**
 * 持久化监听器
 */
public class CustomListener {

    @PrePersist
    public void prePersist(Object obj){
        System.out.println("@PrePersist" + obj);
    }

    @PostPersist
    public void postPersist(Object obj){
        System.out.println("@PostPersist" + obj);
    }
}
