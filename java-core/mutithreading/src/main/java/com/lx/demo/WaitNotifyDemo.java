package com.lx.demo;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: 线程等待唤醒测试, wait notify全部为object方法，
 * java.lang.Object#wait()
 * java.lang.Object#notifyAll()
 *     等待池：假设一个线程A调用了某个对象的wait()方法，线程A就会释放该对象的锁后，进入到了该对象的等待池，等待池中的线程不会去竞争该对象的锁。
 *     锁池：只有获取了对象的锁，线程才能执行对象的 synchronized 代码，对象的锁每次只有一个线程可以获得，其他线程只能在锁池中等待
 *
 *
 *
 *
 *     wait() 和 sleep() 的区别
 *
 *     wait() 是 Object 的方法，而 sleep() 是 Thread 的静态方法；
 *     wait() 会释放锁，sleep() 不会。
 * @date 2021/3/18 上午11:14
 */
public class WaitNotifyDemo {

    private static Object obj = new Object();

    public static void main(String[] args) {
        final WaitNotifyDemo waitNotifyDemo = new WaitNotifyDemo();
//        waitNotifyDemo.testNotify();
        waitNotifyDemo.testNotifyAll();
    }

    /**
     * @data: 2021/3/18-上午11:51
     * @User: zhaozhiwei
     * @method: testNotifyAll

     * @return: void
     * @Description:
     * result: 唤醒所有a, 执行完毕
     * Thread a begin Thread-0
     * Thread c begin
     * Thread a begin Thread-1
     * 睡3秒
     * Thread c end and notify all
     * Thread a end Thread-1
     * Thread a end Thread-0
     */
    public void testNotifyAll(){
        // 创建两个a线程，并等待
        final Thread threadA1 = new Thread(new A(obj));
        final Thread threadA2 = new Thread(new A(obj));
        threadA1.start();
        threadA2.start();

        // 使用c唤醒一个
        final Thread threadC = new Thread(new C(obj));
        // 睡3秒，足够前面两个线程锁住, 代码结果应该是3秒后全部a跑完
        threadC.start();
    }

    /**
     * @data: 2021/3/18-上午11:40
     * @User: zhaozhiwei
     * @method: testNotify

     * @return: void
     * @Description: 唤醒一个线程测试
     * result: 只有一个a能跑完
     * Thread a begin Thread-0
     * Thread b begin Thread-2
     * 睡3秒
     * Thread a begin Thread-1
     * Thread b end Thread-2
     * Thread a end Thread-0
     */
    public void testNotify(){
        // 创建两个a线程，并等待
        final Thread threadA1 = new Thread(new A(obj));
        final Thread threadA2 = new Thread(new A(obj));
        threadA1.start();
        threadA2.start();

        // 使用b唤醒一个
        final Thread threadB = new Thread(new B(obj));
        // 睡3秒，足够前面两个线程锁住, 代码结果应该是3秒后其中一个a跑完
        threadB.start();
        // result
    }

    class A implements Runnable{

        private Object obj;

        public A(Object obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            System.out.println("Thread a begin " + Thread.currentThread().getName());
            synchronized (obj){
                try {
                    obj.wait();
//                    可以设置wait 1500ms,超过就不等了
//                    obj.wait(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread a end " + Thread.currentThread().getName());
            }
        }
    }

    class B implements Runnable{

        private Object obj;

        public B(Object obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            System.out.println("Thread b begin " + Thread.currentThread().getName());
            System.out.println("睡3秒");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj){
                System.out.println("Thread b end " + Thread.currentThread().getName());
                obj.notify();
            }
        }
    }

    class C implements Runnable{

        private Object obj;

        public C(Object obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            System.out.println("Thread c begin");
            System.out.println("睡3秒");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (obj){
                System.out.println("Thread c end and notify all");
                obj.notifyAll();
            }
        }
    }
}
