package com.lx.user;

public enum ResponseCodeEnum {
    USERORPASSWORD_ERRROR("001001","用户名或密码不存在"),
    SUCCESS("000000","成功"),
    SYS_PARAM_NOT_RIGHT("001002","请求参数错误"),
    TOKEN_EXPIRE("001003","token过期"),
    SIGNATURE_ERROR("001004","签名验证失败"),
    SYSTEM_BUSY("001099","系统繁忙，请稍候重试");

    private final String code;
    private final  String msg;

    ResponseCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
