package com.acme.shop.common;

/**
 * 通用返回包装（无业务语义，放 common 层）。
 */
public record Result<T>(int code, String message, T data) {

    public static <T> Result<T> ok(T data) {
        return new Result<>(0, "ok", data);
    }
}
