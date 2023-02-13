package com.example.service.validate;

import com.example.domain.CustomLicenseParamExt;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseContentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @Title: null.java
 * @Package: com.example.service.validate
 * @Description: 数据库连接校验
 * 如: 要求数据库连接是xxx的才允许访问
 * 只能相对静态的一方来控制
 * @author: zhaozhiwei
 * @date: 2022/12/16 下午3:42
 * @version: V1.0
 */
@Component
public class DatabaseConnectValidate implements IValidate{

    @Value("${spring.database.url:127.0.0.1}")
    private String url;

    @Override
    public void validate(LicenseContent content) throws LicenseContentException {
        // 1. 获取数据库url, 解析出ip信息

        // 2. 获取content中要求的ip信息
        CustomLicenseParamExt expectedCheckModel = (CustomLicenseParamExt) content.getExtra();
        final String databaseUrl = expectedCheckModel.getDatabaseUrl();

        if (!StringUtils.isEmpty(databaseUrl)
                && !url.equals(databaseUrl)) {
            throw new LicenseContentException("应用未授权, 数据库地址解析失败");
        }
    }
}
