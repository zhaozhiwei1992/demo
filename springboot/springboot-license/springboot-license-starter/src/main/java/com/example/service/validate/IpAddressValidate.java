package com.example.service.validate;

import com.example.domain.CustomLicenseParamExt;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseContentException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Title: null.java
 * @Package: com.example.service.validate
 * @Description: 校验ip地址
 * @author: zhaozhiwei
 * @date: 2022/12/16 下午2:32
 * @version: V1.0
 */
@Component
public class IpAddressValidate implements IValidate {
    @Override
    public void validate(LicenseContent content) throws LicenseContentException {
        //License中可被允许的参数信息
        CustomLicenseParamExt expectedCheckModel = (CustomLicenseParamExt) content.getExtra();
        //当前服务器真实的参数信息
        CustomLicenseParamExt serverCheckModel = getServerDTO();

        if (expectedCheckModel != null && serverCheckModel != null) {
            final List<String> expectedIpList = expectedCheckModel.getIpAddress();
            final List<String> serverIpList = serverCheckModel.getIpAddress();
            if (expectedIpList != null && expectedIpList.size() > 0) {
                if (serverIpList != null && serverIpList.size() > 0) {
                    if (!expectedIpList.equals(serverIpList)) {
                        throw new LicenseContentException("当前服务器的IP没在授权范围内");
                    }
                }
            }
        }else{
            throw new LicenseContentException("无法获取服务器IP信息");
        }
    }
}
