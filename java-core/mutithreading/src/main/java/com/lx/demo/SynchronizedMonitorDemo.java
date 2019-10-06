package com.lx.demo;

/**
 * monitor相当于是synchronized观测的唯一标识，如果每次变化么得卵用
 */
public class SynchronizedMonitorDemo {
    public static void main(String[] args) {
        final SynchronizedMonitorDemo monitorDemo = new SynchronizedMonitorDemo();
        final Object o = new Object();
        for (int i = 0; i < 5; i++) {
            final Thread thread = new Thread(() -> {
                monitorDemo.sameMonitor(o);
            });
            thread.setName("thread " + i);
            thread.start();
        }

        for (int i = 6; i < 10; i++) {
            final Thread thread = new Thread(() -> {
                monitorDemo.dynamicMonitor();
            });
            thread.setName("thread " + i);
            thread.start();
        }

        for (int i = 10; i < 15; i++) {
            final Thread thread = new Thread(() -> {
                monitorDemo.methodSynchronized();
            });
            thread.setName("thread " + i);
            thread.start();
        }
    }

    /**
     * 方法级别也是全局唯一
     */
    private synchronized void methodSynchronized() {
        System.out.printf("线程 %s access \n", Thread.currentThread().getName());
        while (true) {

        }
    }

    /**
     * 使用同一个monitor, 第一程序线程不结束，锁不释放,后续都执行不了
     * @param o
     */
    private void sameMonitor(Object o) {
        synchronized (o) {
            System.out.printf("线程 %s access \n", Thread.currentThread().getName());
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            while (true) {

            }
        }
    }

    /**
     * synchronized用的monitor必须是同一个才有效，如果是每次动态变化同步就失效了,比如这里会全部输出然后一直死循环
     */
    private void dynamicMonitor() {
        final Object o = new Object();
        synchronized (o) {
            System.out.printf("线程 %s access \n", Thread.currentThread().getName());

            while (true) {

            }
        }
    }
}
