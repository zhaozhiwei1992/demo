package com.acme.shop.user;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

/** 第 2 层守卫（per-biz） */
class UserModuleVerifyTest {

    @Test
    void verifyModuleStructure() {
        ApplicationModules.of("com.acme.shop.user").verify();
    }
}
