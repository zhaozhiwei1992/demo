package com.sixj.license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用于生成License证书
 */
@SpringBootApplication
public class LicenseCreatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LicenseCreatorApplication.class, args);
    }

}
