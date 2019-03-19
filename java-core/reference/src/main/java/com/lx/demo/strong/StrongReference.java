package com.lx.demo.strong;

/**
 * 强引用， gc后不会释放
 */
public class StrongReference {

    private static Object strongRef = new Object();

    public static void main(String[] args) {
        Object obj = strongRef;
        strongRef = null;
        System.gc();
        System.out.println("gc之后: " + obj);
    }
}
