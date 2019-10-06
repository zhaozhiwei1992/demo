package com.lx.demo;

/**
 * 相同的变量不相等
 */
public class SameVarNotEqualsTest {
    public static void main(String[] args) {
        final double i = Double.NaN;
//        final double i = Double.valueOf(1);
        System.out.println(i!=i);
    }
}
