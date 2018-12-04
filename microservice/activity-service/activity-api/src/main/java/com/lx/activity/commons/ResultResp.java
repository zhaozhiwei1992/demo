package com.lx.activity.commons;


import java.io.Serializable;

/**
 * 统一的返回结果
 * @param <T>
 */
public class ResultResp<T> implements Serializable {

    private static final long serialVersionUID = 8140875256110329513L;

    /**
     * 返回码
     */
    private ReturnCodeEnum returnCodeEnum;

    /**
     * 某个具体类
     */
    private T result;

    public ReturnCodeEnum getReturnCodeEnum() {
        return returnCodeEnum;
    }

    public void setReturnCodeEnum(ReturnCodeEnum returnCodeEnum) {
        this.returnCodeEnum = returnCodeEnum;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultResp{" +
                "result=" + result +
                ", returnCodeEnum=" + returnCodeEnum +
                '}';
    }
}
