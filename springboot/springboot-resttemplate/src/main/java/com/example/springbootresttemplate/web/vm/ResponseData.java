package com.example.springbootresttemplate.web.vm;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 5704430076796818950L;

    private String code;
    private String msg;
    private Date timestamps;
    private T data;
    private Integer count;
}
