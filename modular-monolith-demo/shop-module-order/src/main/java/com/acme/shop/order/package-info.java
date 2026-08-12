/**
 * 订单模块。跨模块引用只允许声明在 allowedDependencies 里的模块；
 * 本模块对外的契约只有 api 包（@NamedInterface("api")）。
 */
@org.springframework.modulith.ApplicationModule(allowedDependencies = {"common"}, displayName = "订单模块")
package com.acme.shop.order;
