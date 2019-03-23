package com.lx.demo.soft;

import java.lang.ref.SoftReference;

/**
 * 软引用
 * 只有发生oom， 内存不足异常才会发生回收
 */
public class SoftReferenceDemo {

    public static void main(String[] args) {
        Object o = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o);
        o = null;
        System.gc();
        System.out.println("gc之后: " + softReference.get());
    }
}
