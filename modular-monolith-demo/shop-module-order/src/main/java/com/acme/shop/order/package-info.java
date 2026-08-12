/**
 * 订单模块（核心模块：DDD 五层）。
 * api(契约) / application(用例+事务) / domain(聚合+仓库接口，无 Spring) /
 * infrastructure(仓库实现，依赖倒置) / web(HTTP，只碰 api)。
 * per-biz verify 时这些子包被当作小模块，自动做环检测与依赖方向检查。
 */
@org.springframework.modulith.ApplicationModule(allowedDependencies = {"common"}, displayName = "订单模块（核心）")
package com.acme.shop.order;
