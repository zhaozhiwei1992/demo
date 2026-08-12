package com.acme.shop.common;

import com.acme.shop.order.service.OrderServiceImpl;

/**
 * 违规样例（故意放在 src 之外，不要编译进去）：
 * common 模块直接 import 了 order 模块的内部实现类（service 包 = 模块内部，非 api 契约）。
 *
 * 演示方法：把本文件拷到
 *   shop-common/src/main/java/com/acme/shop/common/
 * 然后重新执行 mvn clean package —— 会看到【编译失败】：
 *   "程序包com.acme.shop.order.service不存在"
 * 这正是第 1 层守卫（Maven 物理隔离）在编译期就拦住了 biz 互引，比 Modulith 更早。
 * 删掉该文件即可还原。
 *
 * 说明：Modulith（第 2 层）拦的是「能过 Maven 的包级违规」——即模块间存在
 * artifact 级依赖（如 order 依赖 payment-api 契约工程）却去 import 对方 internal
 * 实现类的场景，由 shop-server 的聚合 verify 拦截（见笔记第四节）。
 */
public class BadLeak {

    OrderServiceImpl service; // 违规：跨模块访问 internal（被第 1 层 Maven 硬墙拦截）
}
