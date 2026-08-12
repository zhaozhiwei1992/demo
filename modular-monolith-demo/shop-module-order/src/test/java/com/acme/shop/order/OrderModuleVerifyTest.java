package com.acme.shop.order;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

/**
 * 第 2 层守卫（per-biz）：本模块内部结构自检。
 * 只扫描 com.acme.shop.order 包，秒级完成，失败信息贴近本模块。
 */
class OrderModuleVerifyTest {

    @Test
    void verifyModuleStructure() {
        ApplicationModules.of("com.acme.shop.order").verify();
    }
}
