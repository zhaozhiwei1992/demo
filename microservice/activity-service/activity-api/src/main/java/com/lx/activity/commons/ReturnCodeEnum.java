package com.lx.activity.commons;

/**
 * 返回码对照表
 * 营销活动的错误码: 100-3位递增
 */
public enum ReturnCodeEnum {

    SUCCESS("000000", "成功"),
    SYSTEM_ERROR("100999", "系统繁忙,请稍后重试"),
    PARAMETER_NULL("100001", "请求参数不正确");

    private String code;

    private String msg;

    private ReturnCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getCodeString(){
        return getCode()+"";
    }
    @Override
    public String toString() {
        return "ReturnCodeEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
