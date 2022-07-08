package com.example.aspect;

import com.example.service.LicenseService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: LicenseAspect
 * @Package com/example/aspect/LicenseAspect.java
 * @Description: 通过拦截器的方式校验某些接口license是否有效
 * 一般来说启动时候已经校验，这个适用性一般, 除非好几年不重启服务
 * @date 2022/7/8 下午1:58
 */
@Aspect
@Order(1)
public class LicenseAspect {
    @Pointcut("@annotation(com.example.aspect.License)")
    public void isLicensePointcut() {
    }

    @Before("isLicensePointcut()")
    public void beforeIsLicensePointcutCheck(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        License license = method.getAnnotation(License.class);
        if (!Objects.isNull(license)) {
            LicenseService licenseVerify = new LicenseService();
            //1. 校验证书是否有效
            boolean verifyResult = licenseVerify.verify();
            if (!verifyResult) {
                //抛异常
                throw new RuntimeException("您的证书无效，请核查服务器是否取得授权或重新申请证书");
            }
        }
    }
}
