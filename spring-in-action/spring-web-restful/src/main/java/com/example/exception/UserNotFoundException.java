package com.example.exception;

public class UserNotFoundException extends RuntimeException{

    private long id;

    public UserNotFoundException(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
