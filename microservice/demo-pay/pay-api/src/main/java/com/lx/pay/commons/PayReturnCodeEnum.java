package com.lx.pay.commons;
/**
 * 返回码对照表
 * 支付的错误码: 006-3位递增
 */
public enum PayReturnCodeEnum {

    SUCCESS("000000", "成功"),
    SYSTEM_ERROR("006999", "系统繁忙,请稍后重试"),
    SYS_PARAM_NOT_RIGHT("006001", "请求参数不正确"),
    PAYMENT_PROCESSOR_FAILED("006002","支付失败");


    private String code;

    private String msg;

    private PayReturnCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getMsg(String code) {
        return msg+":"+code;
    }

    public String getCodeString(){
        return getCode()+"";
    }
    @Override
    public String toString() {
        return "PayReturnCodeEnum{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
