package com.lx.demo;

import lombok.Data;

import java.io.Serializable;

@Data
public class DoOrderResponse implements Serializable {
    private static final long serialVersionUID = 7058875504943795379L;
    private Object data;
    private String code;
    private String memo;
}
