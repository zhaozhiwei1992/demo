package com.example.springbootsecurityoauth2.domain;

import java.time.LocalDateTime;

public class SmsCode {
    private String code;
    private LocalDateTime expireTime;


    public SmsCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpried() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "SmsCode{" +
                "code='" + code + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}