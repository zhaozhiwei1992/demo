package com.lx.demo.springbooteasyexcel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class DemoData {
    @ExcelProperty("字符串")
    private String string;
    @ExcelProperty("日期")
    private Date date;
    private Double doubleData;
}
