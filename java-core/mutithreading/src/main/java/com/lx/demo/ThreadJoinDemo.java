package com.lx.demo;

public class ThreadJoinDemo {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 20; i++) {

            final Thread t1 = new Thread(ThreadJoinDemo::sayHello);
            t1.setName("t1");
            t1.start();
//            t1.join();

            final Thread t2 = new Thread(ThreadJoinDemo::sayHello);
            t2.setName("t2");
            t2.start();

            // join
//            t2.join();
//            t1.join();
//            Thread.sleep(200);
            // thread exec before mainthread sout

            System.out.println("exec in mainthread"); // exec after join
        }
    }

    public static void sayHello(){
        System.out.printf("thread [name: %s] say hello \n", Thread.currentThread().getName());
    }
}
