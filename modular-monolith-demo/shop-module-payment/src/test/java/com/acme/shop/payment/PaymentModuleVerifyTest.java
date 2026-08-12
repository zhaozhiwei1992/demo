package com.acme.shop.payment;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

/** 第 2 层守卫（per-biz） */
class PaymentModuleVerifyTest {

    @Test
    void verifyModuleStructure() {
        ApplicationModules.of("com.acme.shop.payment").verify();
    }
}
