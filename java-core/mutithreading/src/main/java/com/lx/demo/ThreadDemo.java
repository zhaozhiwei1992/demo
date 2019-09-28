package com.lx.demo;

/**
 *
 */
public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        final Thread thread = new Thread(ThreadDemo::sayHello);
        thread.start();
        // Waits for this thread to die /TERMINATED
        thread.join();
        System.out.println("exec in mainthread"); // exec after join
        System.out.println(thread.getState()); //
    }

    public static void sayHello(){
        System.out.printf("thread [id: %s] say hello \n", Thread.currentThread().getId());
    }
}
