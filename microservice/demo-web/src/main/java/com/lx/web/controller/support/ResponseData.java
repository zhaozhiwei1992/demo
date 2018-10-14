package com.lx.web.controller.support;


/**
 * @author 赵志伟
 * @ClassName: ResponseData
 * @description [描述该类的功能]
 * @create 2018-07-10 下午2:17
 **/
public class ResponseData {
    private String code;

    private String message;

    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
