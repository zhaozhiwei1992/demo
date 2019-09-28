package com.lx.demo;

import java.util.concurrent.atomic.AtomicInteger;

public class StopThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            final StopAction stopAction = new StopAction();
            final Thread thread = new Thread(stopAction);
//        stopAction.stop(); //stoped == true thread runnable
            thread.start();
            stopAction.stop(); //stoped == true thread blocked
        }
        System.out.println("exec in mainthread"); // exec after join
//        System.out.println(thread.getState()); //
    }

    public static class StopAction implements Runnable{

        /**
         * atomic
         * {@link AtomicInteger}
         */
//        private volatile boolean stoped;
        private boolean stoped;

        @Override
        public void run() {
            if(stoped){
                System.out.println("thread is stoped");
                return;
            }
            System.out.println("thread is running");
        }

        public void stop(){
            this.stoped = true;
        }
    }
}
