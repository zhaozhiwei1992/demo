package com.lx.demo.exceptiononspringmvc.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResultVO {

    private String msg;

    private String result;

    private String code;

    private Date time;

}
