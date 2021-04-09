package com.lx.demo;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: TODO
 * @date 2021/4/9 下午3:30
 */
public class ThreadLocalDemo {

    public static void main(String[] args) {

        final ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<>();

        final Thread thread1 = new Thread(() -> {
            integerThreadLocal.set(1);
            System.out.println("线程1设置结束");
            try {
                // 等待线程2设置结束， threadlocal中值仍然是1
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程1当前值: " + integerThreadLocal.get());
        });

        final Thread thread2 = new Thread(() -> {
            integerThreadLocal.set(2);
            System.out.println("线程1设置结束");
        });

        thread1.start();
        thread2.start();
    }
}
