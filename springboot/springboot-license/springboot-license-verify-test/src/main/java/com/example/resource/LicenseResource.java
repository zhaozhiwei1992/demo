package com.example.resource;

import com.example.aspect.License;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: TestLicenseController
 * @Package com/sixj/test/controller/TestLicenseController.java
 * @Description: 测试license是否生效
 * 一些核心接口，比如登陆接口，添加@License注解，
 * 请求该接口的时候，也会去验证授权证书的有效性，比如验证证书是否到期，如果失效，该接口将会拒绝访问。
 *
 * 防止死活不重启
 * @author zhaozhiwei
 * @date 2022/7/7 下午9:39
 * @version V1.0
 */
@RestController
@RequestMapping("/license")
public class LicenseResource {

    @GetMapping("/test")
    @License
    public String test(){
        return "success";
    }
}
