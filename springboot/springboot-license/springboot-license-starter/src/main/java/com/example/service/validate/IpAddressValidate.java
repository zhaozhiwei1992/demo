package com.example.service.validate;

import com.example.service.dto.HardWareParamDTO;
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
        HardWareParamDTO expectedCheckModel = (HardWareParamDTO) content.getExtra();
        //当前服务器真实的参数信息
        HardWareParamDTO serverCheckModel = getServerDTO();

        if (expectedCheckModel != null && serverCheckModel != null) {
            final List<String> expectedIpList = expectedCheckModel.getIpAddress();
            final List<String> serverIpList = serverCheckModel.getIpAddress();
            if (expectedIpList != null && expectedIpList.size() > 0) {
                if (serverIpList != null && serverIpList.size() > 0) {
                    for (String expected : expectedIpList) {
                        if (!serverIpList.contains(expected.trim())) {
                            throw new LicenseContentException("当前服务器的IP没在授权范围内");
                        }
                    }
                }
                throw new LicenseContentException("当前服务器的IP没在授权范围内");
            }
        }else{
            throw new LicenseContentException("无法获取服务器IP信息");
        }
    }
}
