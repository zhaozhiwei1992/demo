package com.example.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.config
 * @Description:
 * AbstractSecurityWebApplicationInitializer实现了
 * WebApplication-Initializer，因此Spring会发现它，并用它
 * 在Web容器中注册DelegatingFilterProxy, 不需要重载任何方法。
 * @date 2022/2/26 下午11:26
 */
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
}
