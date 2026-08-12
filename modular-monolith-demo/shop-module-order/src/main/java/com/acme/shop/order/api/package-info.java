/**
 * api 包 = 本模块对外契约（接口 + DTO）。
 * 【不要】在这里写 @NamedInterface("api")——写了会导致 per-biz verify 报
 * "Module 'web' depends on non-exposed type ... within module 'api'"。
 * 只有出现跨模块调用需求（其他模块要 order::api）时才加 @NamedInterface，
 * 届时契约校验交给 shop-server 的聚合 verify。
 */
package com.acme.shop.order.api;
