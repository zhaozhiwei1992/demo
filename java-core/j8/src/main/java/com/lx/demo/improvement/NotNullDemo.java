package com.lx.demo.improvement;

import java.util.List;
import java.util.Objects;

/**
 * @Description 大佬写点东西吧
 * Exception in thread "main" java.lang.NullPointerException: s不能空
 * 	at java.util.Objects.requireNonNull(Objects.java:290)
 * 	at com.lx.demo.improvement.NotNullDemo.main(NotNullDemo.java:14)
 * @auther lx7ly
 * @create 2019-10-25 下午2:15
 */
public class NotNullDemo {
    public static void main(String[] args) {
        String s = null;
        Objects.requireNonNull(s, () -> "s不能空");
        System.out.println(s);
    }
}
