package com.lx.web.controller.support;

import lombok.Data;

/**
 * @author 赵志伟
 * @ClassName: ResponseData
 * @description [描述该类的功能]
 * @create 2018-07-10 下午2:17
 **/
@Data
public class ResponseData {
    private String code;

    private String message;

    private Object data;
}
