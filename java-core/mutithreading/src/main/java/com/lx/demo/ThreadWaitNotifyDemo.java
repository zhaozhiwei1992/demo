package com.lx.demo;

public class ThreadWaitNotifyDemo {

    public static void main(String[] args) throws InterruptedException {
        // t1, t2 waiting main
        final Thread t1 = new Thread(ThreadWaitNotifyDemo::sayHello);
        t1.setName("t1");
        t1.start();

        final Thread t2 = new Thread(ThreadWaitNotifyDemo::sayHello);
        t2.setName("t2");
        t2.start();
//        t2.join();
//        t1.join();

        final Object monitor = ThreadWaitNotifyDemo.class;
        synchronized (monitor){
            //Exception in thread "main" java.lang.IllegalMonitorStateException
            // cant notify by yourself
//        t1.notify();
//        t2.notify();

//            monitor.notify(); // notify first one
            monitor.notifyAll();
        }
        System.out.println("exec in mainthread"); // exec after join
    }

    public static void sayHello(){
        final Thread currentThread = Thread.currentThread();

        final Object monitor = ThreadWaitNotifyDemo.class;
        synchronized (monitor){
            try {
                System.out.printf("thread [name: %s] waiting \n", currentThread.getName());
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("thread [name: %s] running \n", currentThread.getName());
    }
}
