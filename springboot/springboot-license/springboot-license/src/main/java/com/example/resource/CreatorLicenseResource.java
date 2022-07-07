package com.example.resource;

import com.example.service.LicenseService;
import com.example.domain.CustomLicenseParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 生成证书的
 * 
 */
@RestController
@RequestMapping("/license")
public class CreatorLicenseResource {

    /**
     * 生成证书
     * @param param 证书生成参数
     * @return
     */
    @PostMapping(value = "/generateLicense")
    public Map<String,Object> generateLicense(@RequestBody CustomLicenseParam param) {
        Map<String,Object> resultMap = new HashMap<>(2);
        /* 调用生成证书*/
        LicenseService licenseCreator = new LicenseService(param);
        boolean result = licenseCreator.generateLicense();
        if(result){
            resultMap.put("result","ok");
            resultMap.put("msg",param);
        }else{
            resultMap.put("result","error");
            resultMap.put("msg","证书文件生成失败！");
        }
        return resultMap;
    }
}
