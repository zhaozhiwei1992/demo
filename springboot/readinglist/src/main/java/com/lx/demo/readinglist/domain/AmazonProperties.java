package com.lx.demo.readinglist.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * ConfigurationProperties //属性注入
 */
@Component
@ConfigurationProperties("amazon")
public class AmazonProperties {

    private String associateId;
    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }
    public String getAssociateId() {
        return associateId;
    }
}
