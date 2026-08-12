package com.acme.shop;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

/**
 * 第 2 层守卫（聚合）：只有这里所有业务模块都在类路径上，因此：
 *  - 跨模块 internal 泄漏（模块间存在 artifact 依赖时，如 order 依赖 payment-api
 *    却 import payment 的 internal 实现类）→ 报 Violations
 *  - 各模块 allowedDependencies 声明是否真实成立（引用了不存在的模块/命名接口）→ 这里才校验
 * per-biz verify（各业务模块内）只管本模块内部结构，拦不了跨模块问题。
 */
class ShopModulithVerifyTest {

    @Test
    void verifyAggregateModules() {
        var modules = ApplicationModules.of("com.acme.shop");
        modules.forEach(m ->
            System.out.println("[module] " + m.getName() + " -> " + m.getBasePackage()));
        modules.verify(); // 有违规直接抛异常
    }
}
