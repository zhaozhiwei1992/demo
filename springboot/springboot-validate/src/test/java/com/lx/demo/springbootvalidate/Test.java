package com.lx.demo.springbootvalidate;

import org.springframework.util.StringUtils;

public class Test {
    @org.junit.Test
    public void testStringUtil(){
        String s = "";
        String[] strings = StringUtils.delimitedListToStringArray(s, "-");
        System.out.println(strings[0]);
    }
}
