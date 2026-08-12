package com.acme.shop.user.api;

/** 用户模块对外契约（非核心模块：三层结构 api/service/web）。 */
public interface UserApi {

    String currentUser();
}
