package com.lx.demo;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试synchronized使用
 * 字节码
 * 进入class目录下，然后javap -v -p SynchronizedDemo
 * {@see tmp.txt}
 */
public class SynchronizedDemo {
    public static void main(String[] args) {
        String str = "helloworld";
        echo(str);
        doEcho(str);

        final SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
        final SynchronizedDemo synchronizedDemo2 = new SynchronizedDemo();
        final ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            synchronizedDemo.func1();
        });

        executorService.execute(() -> {
            synchronizedDemo.func1();
        });

        executorService.execute(() -> {
            synchronizedDemo2.func1();
        });

    }

    /**
     * @data: 2021/3/18-上午9:27
     * @User: zhaozhiwei
     * @method: func1

     * @return: void
     * @Description: 线程等待, 基于对象，不同对象不同步
     */
    public void func1() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.print(i + " ");
            }
        }
    }

    public void func2() {
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
        }
    }

    private static void echo(String helloworld) {
        synchronized (SynchronizedDemo.class){
            doEcho(helloworld);
        }
    }

    private synchronized static void doEcho(String helloworld) {
        System.out.println(helloworld);
    }
}
