package com.lx.demo;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: 创建两个线程, a, b 保证a在b之前执行， b中要把a join进来
 * @date 2021/3/18 上午10:53
 */
public class JoinDemo {

    class A extends Thread{

        @Override
        public void run() {
            System.out.println("Thread a is running");
        }
    }

    class B extends Thread{

        private A a;

        public B(A a) {
            this.a = a;
        }

        @Override
        public void run() {
            try {
                // a加入进来必须等a执行完
                a.join();
                System.out.println("Thread b is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @data: 2021/3/18-上午11:00
     * @User: zhaozhiwei
     * @method: test

     * @return: void
     * @Description:
     * result:
     * Thread a is running
     * Thread b is running
     */
    public void test(){
        final A a = new A();
        final B b = new B(a);
        b.start();
        a.start();
    }

    public static void main(String[] args) {
        final JoinDemo joinDemo = new JoinDemo();
        joinDemo.test();
    }
}
