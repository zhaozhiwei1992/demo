package com.lx.demo;

public class ThreadInterruptDemo {

    public static void main(String[] args) throws InterruptedException {
        final Thread thread = new Thread(ThreadInterruptDemo::sayHello);
        thread.start(); //1000
        Thread.sleep(500); //500
        // thread.interrupt() 方法在 start() 调用之前是没有效
        // interrupt() 并不能中止线程，但是可以传递 interrupt 状态
        thread.interrupt();
        // Waits for this thread to die /TERMINATED
        thread.join();
        System.out.println("exec in mainthread"); // exec after join
    }

    /**
     *
     */
    public static void sayHello(){
        if(Thread.interrupted()){
            System.out.printf("thread [id: %s] say hello is interrupted \n", Thread.currentThread().getId());
            return;
        }

        // thread.sleep(1000)
        final Object monitor = ThreadInterruptDemo.class;
        synchronized (monitor){
            try {
                // interrupted when waitint throw exception
//                Thread.sleep(500); //500
                monitor.wait(1000);
            } catch (InterruptedException e) {
                // 当前 interrupted 状态被清除 == false
                e.printStackTrace();
                System.out.printf("thread [id: %s] say hello status is %s \n"
                        , Thread.currentThread().getId()
                        , Thread.currentThread().isInterrupted()); //false
                return; //?
            }
        }

        System.out.printf("thread [id: %s] say hello \n", Thread.currentThread().getId());
    }
}
