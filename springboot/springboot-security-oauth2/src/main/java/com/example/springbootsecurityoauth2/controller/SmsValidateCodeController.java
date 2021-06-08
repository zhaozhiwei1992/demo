package com.example.springbootsecurityoauth2.controller;

import com.example.springbootsecurityoauth2.domain.SmsCode;
import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: SmsValidateCodeController
 * @Package com/lx/demo/springbootsecurity/controller/SmsValidateCodeController.java
 * @Description: 验证码肯定会记录在服务器某个地方,等待比对, 有失效时间
 * 这里直接存到本地缓存中，或者可以写入到一个服务缓存中即可
 * @author zhaozhiwei
 * @date 2021/6/6 下午10:21
 * @version V1.0
 */
@RestController
public class SmsValidateCodeController {

    @Autowired
    Cache<String, Object> caffeineCache;

    /**
     * @data: 2021/6/8-下午11:28
     * @User: zhaozhiwei
     * @method: createCode
      * @param deviceId :
 * @param mobile :
     * @return: void
     * @Description: 描述
     * curl -X GET -H 'deviceId: 001' -i 'http://localhost:8080/code/sms?mobile=18234837162'
     */
    @GetMapping("/code/sms")
    public SmsCode createCode(@RequestHeader("deviceId") String deviceId, String mobile) {
        SmsCode smsCode = createSmsCode();
        System.out.println("验证码发送成功：" + smsCode);

        String key = "code:sms:"+ deviceId;
        caffeineCache.put(key, smsCode.getCode());
        return smsCode;
    }

    private SmsCode createSmsCode() {
        String code = (int) ((Math.random() * 9 + 1) * 100000) + "";
        return new SmsCode(code, 600);
    }
}