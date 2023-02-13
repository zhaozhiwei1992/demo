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
public class MacAddressValidate implements IValidate {
    @Override
    public void validate(LicenseContent content) throws LicenseContentException {
        //License中可被允许的参数信息
        CustomLicenseParamExt expectedCheckModel = (CustomLicenseParamExt) content.getExtra();
        //当前服务器真实的参数信息
        CustomLicenseParamExt serverCheckModel = getServerDTO();

        if (expectedCheckModel != null && serverCheckModel != null) {
            final List<String> expectedMacAddress = expectedCheckModel.getMacAddress();
            final List<String> serverMacList = serverCheckModel.getMacAddress();
            if (expectedMacAddress != null && expectedMacAddress.size() > 0) {
                if (serverMacList != null && serverMacList.size() > 0) {
                    if(!expectedMacAddress.equals(serverMacList)){
                        throw new LicenseContentException("当前服务器的Mac地址没在授权范围内");
                    }
                }
            }
        }else{
            throw new LicenseContentException("无法获取服务器Mac信息");
        }
    }
}
