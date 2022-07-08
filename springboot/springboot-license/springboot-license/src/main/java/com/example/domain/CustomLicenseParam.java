package com.example.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: CustomLicenseParam
 * @Package com/example/domain/CustomLicenseParam.java
 * @Description: License生成需要的参数实体类
 * @date 2022/7/8 上午11:31
 */
@Data
@ConfigurationProperties(prefix = "license")
public class CustomLicenseParam implements Serializable {
    /**
     * 证书subject
     */
    private String subject;

    /**
     * 密钥别称
     */
    private String privateAlias;

    /**
     * 密钥密码（需要妥善保管，不能让使用者知道）
     */
    private String keyPass;

    /**
     * 访问秘钥库的密码
     */
    private String storePass;

    /**
     * 证书生成路径
     */
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    private String privateKeysStorePath;

    /**
     * 证书生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //接收时间类型
    private Date issuedTime = new Date();

    /**
     * 证书失效时间
     * 使用DateTimeFormat指定接收时间类型
     * 处理yaml文件转日期类型时转换报错: Failed to convert from type [java.lang.String] to type
     * [@com.fasterxml.jackson.annotation.JsonFormat java.util.Date] for value '2022-07-07 19:07:00'
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expiryTime;

    /**
     * 用户类型
     */
    private String consumerType = "user";

    /**
     * 用户数量
     */
    private Integer consumerAmount = 1;

    /**
     * 描述信息
     */
    private String description = "";

    /**
     * 额外的服务器硬件校验信息，无需额外校验可去掉
     */
    private CustomLicenseParamExt customLicenseParamExt;
}
