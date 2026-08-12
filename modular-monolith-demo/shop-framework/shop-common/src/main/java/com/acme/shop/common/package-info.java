/**
 * common 是「共享模块」：允许被所有业务模块依赖，但它自己不依赖任何业务模块。
 */
@org.springframework.modulith.ApplicationModule(allowedDependencies = {}, displayName = "通用层")
package com.acme.shop.common;
