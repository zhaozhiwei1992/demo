package com.lx.pay.biz.abs;


import com.lx.pay.commons.AbstractRequest;

/**
 * 数据验证接口类
 */
public interface Validator {

    void validate(AbstractRequest request);
}
