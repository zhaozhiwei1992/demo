package com.lx.demo.weak;

import java.lang.ref.WeakReference;

/**
 * 弱引用， gc后就会被回收
 */
public class WeakReferenceDemo {

    public static void main(String[] args) {
        //String特殊，存储与常量池，不会被回收
//        String o = "xx";
        Object o = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o);
        o = null;
        //虚引用，指向对象被回收，那它就被回收
        System.gc();
        System.out.println(weakReference.get());
    }
}
