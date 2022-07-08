package com.example.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @Title: CustomLicenseParam
 * @Package com/example/configuration/CustomLicenseParam.java
 * @Description: License认证需要的参数实体类
 * @author zhaozhiwei
 * @date 2022/7/8 上午10:33
 * @version V1.0
 */
@Configuration
@ConfigurationProperties(prefix = "license")
@Data
public class CustomLicenseParam implements Serializable {
    /**
     * 证书subject
     */
    private String subject;

    /**
     * 公钥别称
     */
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    private String storePass;

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    private String publicKeysStorePath;
}
