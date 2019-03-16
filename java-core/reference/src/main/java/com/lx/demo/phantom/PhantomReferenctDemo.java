package com.lx.demo.phantom;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用,  必须与@see ReferenceQueue使用
 */
public class PhantomReferenctDemo {

    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue referenceQueue = new ReferenceQueue();
        Object o = new Object();
        PhantomReference phantomReference = new PhantomReference<>(o, referenceQueue);
        o = null;
        System.gc();
        System.out.println("gc之后: " + phantomReference.get());
        Thread.sleep(200);
        //gc之后 会把引用放入到引用queue中, 可以在对象被回收时候做一些操作
        System.out.println("gc之后: " + phantomReference.get() + " queue: " + referenceQueue.poll());

    }
}
