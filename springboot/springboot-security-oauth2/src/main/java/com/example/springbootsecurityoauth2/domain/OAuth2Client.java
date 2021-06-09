package com.example.springbootsecurityoauth2.domain;

/**
 * @Title: OAuth2Client
 * @Package com/example/springbootsecurityoauth2/domain/OAuth2Client.java
 * @Description: 认证信息实体类
 * @author zhaozhiwei
 * @date 2021/6/9 下午4:03
 * @version V1.0
 */
public class OAuth2Client {

    private String clientId;
    private String clientSecret;
    private int accessTokenValiditySeconds;

    public OAuth2Client() {
    }

    public OAuth2Client(String clientId, String clientSecret, int accessTokenValiditySeconds) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    @Override
    public String toString() {
        return "OAuth2Client{" +
                "clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", accessTokenValiditySeconds=" + accessTokenValiditySeconds +
                '}';
    }
}