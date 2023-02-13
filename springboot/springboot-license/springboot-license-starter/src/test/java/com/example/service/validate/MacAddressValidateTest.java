package com.example.service.validate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: JUnit5 Test Class.java.java
 * @Package: com.example.service.validate
 * @Description: TODO
 * @author: zhaozhiwei
 * @date: 2023/2/13 下午5:02
 * @version: V1.0
 */
class MacAddressValidateTest {

    void validate() {
        // 测试
    }

    public static void main(String[] args) {
        // 测试list是否相等
        final List<String> list1 = new ArrayList<>();
        list1.add("1-1");
        list1.add("2-2");
        final List<String> list2 = new ArrayList<>();
        list2.add("1-1");
        list2.add("2-2");
        System.out.println(list1.equals(list2));

    }
}