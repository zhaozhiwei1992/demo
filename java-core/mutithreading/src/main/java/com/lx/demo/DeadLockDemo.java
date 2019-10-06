package com.lx.demo;

/**
 * 死锁
 * 你要我我要你，互不想让
 * jstack查看死锁, 运行程序-> jps找到进程id -> jstack 进程id {@see deadlock.txt}
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        final DeadLockDemo deadLockDemo = new DeadLockDemo();

        final Object o1 = new Object();
        final Object o2 = new Object();

//        deadLockDemo.deadLockV1(o1, o2);
        deadLockDemo.deadLockV2(o1, o2);
    }

    private void deadLockV2(Object o1, Object o2) {
        final Thread thread1 = new Thread(()->{
            echo(o1, o2);
        });
        thread1.setName("thread1");

        final Thread thread2 = new Thread(()->{
            echo(o2, o1);
        });
        thread2.setName("thread2");
        thread1.start();
        thread2.start();
    }

    private void echo(Object o1, Object o2) {
        synchronized (o1){
            System.out.printf("线程[%s] is access, 当前对象 %s\n", Thread.currentThread().getName(), o1);
            try {
                // 加入线程等待，防止一个线程跑太快，让出时间片让另一个线程拿到锁
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (o2){
                System.out.printf("线程[%s] is access, 当前对象 %s\n", Thread.currentThread().getName(), o2);
            }
        }
    }

    /**
     * 这种可能某个线程有延时另一个就运行完不锁了
     * @param o1
     * @param o2
     */
    private void deadLockV1(Object o1, Object o2) {
        final Thread thread1 = new Thread(()->{
            synchronized (o1){
                System.out.printf("线程[%s] is access, 当前对象 %s\n", Thread.currentThread().getName(), o1);
                synchronized (o2){
                    System.out.printf("线程[%s] is access, 当前对象 %s\n", Thread.currentThread().getName(), o2);
                }
            }
        });
        thread1.setName("thread1");

        final Thread thread2 = new Thread(()->{
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            synchronized (o2){
                System.out.printf("线程[%s] is access, 当前对象 %s\n", Thread.currentThread().getName(), o2);
                synchronized (o1){
                    System.out.printf("线程[%s] is access, 当前对象 %s\n", Thread.currentThread().getName(), o1);
                }
            }
        });
        thread2.setName("thread2");

        thread1.start();
        thread2.start();
//        线程[thread1] is access, 当前对象 java.lang.Object@7142e717
//        线程[thread2] is access, 当前对象 java.lang.Object@7142e718
    }
}
