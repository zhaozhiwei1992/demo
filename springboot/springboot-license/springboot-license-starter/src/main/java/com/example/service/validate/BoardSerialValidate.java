package com.example.service.validate;

import com.example.domain.CustomLicenseParamExt;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseContentException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Title: null.java
 * @Package: com.example.service.validate
 * @Description: 校验主板序列号
 * @author: zhaozhiwei
 * @date: 2022/12/16 下午2:32
 * @version: V1.0
 */
@Component
public class BoardSerialValidate implements IValidate {
    @Override
    public void validate(LicenseContent content) throws LicenseContentException {
        //License中可被允许的参数信息
        // 这里要注意, 反序列化要和生成文件时使用的序列化类一致 springboot-license/com.example.domain.CustomLicenseParamExt
        // 否则context.getExtra为空
        CustomLicenseParamExt expectedCheckModel = (CustomLicenseParamExt) content.getExtra();
        //当前服务器真实的参数信息
        CustomLicenseParamExt serverCheckModel = getServerDTO();

        if (expectedCheckModel != null && serverCheckModel != null) {
            final String expectedSerial = expectedCheckModel.getMainBoardSerial();
            final String serverSerial = serverCheckModel.getMainBoardSerial();

            if (!StringUtils.isEmpty(expectedSerial)
                    && !StringUtils.isEmpty(serverSerial)
                    && !expectedSerial.equals(serverSerial)) {
                throw new LicenseContentException("当前服务器的主板序列号没在授权范围内");
            }
        }else{
            throw new LicenseContentException("无法获取服务器主板序列号信息");
        }
    }
}
