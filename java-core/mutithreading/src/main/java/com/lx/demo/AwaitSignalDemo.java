package com.lx.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title: AwaitSignalDemo
 * @Package com/lx/demo/AwaitSignalDemo.java
 * @Description:
 * 我们知道 java 显式锁 Lock 可以认为是对 synchronized 的升级，所以 java 1.5 出现的显式协作 Condition 接口的 await、signal、signalAll 也可以说是普通并发协作
 * wait、notify、notifyAll 的升级；
 *
 * 普通并发协作 wait、notify、notifyAll 需要与 synchronized 配合使用，显式协作 Condition 的 await、signal、signalAll 需要与显式锁 Lock 配合使用（Lock
 * .newCondition()），调用 await、signal、signalAll 方法都必须在 lock 保护之内。
 *
 * 和 wait 一样，await 在进入等待队列后会释放锁和 cpu，当被其他线程唤醒或者超时或中断后都需要重新获取锁，获取锁后才会从 await 方法中退出，await 同样和 wait
 * 一样存在等待返回不代表条件成立的问题，所以也需要主动循环条件判断；
 *
 * await 提供了比 wait 更加强大的机制，譬如提供了可中断或者不可中断的 await 机制等；
 *
 * 特别注意 Condition 也有 wait、notify、notifyAll 方法，因为其也是 Object，所以在使用显式协作机制时千万不要和 synchronized 情况下的协作机制混合使用，避免出现诡异问题。
 *
 * 效果同{@see com.lx.demo.WaitNotifyDemo}
 *
 * @author zhaozhiwei
 * @date 2021/3/18 下午2:19
 * @version V1.0
 */
public class AwaitSignalDemo {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public static void main(String[] args) {
        final AwaitSignalDemo waitNotifyDemo = new AwaitSignalDemo();
//        waitNotifyDemo.testSignal();
        waitNotifyDemo.testSignalAll();
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
    public void testSignalAll(){
        // 创建两个a线程，并等待
        final Thread threadA1 = new Thread(new A());
        final Thread threadA2 = new Thread(new A());
        threadA1.start();
        threadA2.start();

        // 使用c唤醒一个
        final Thread threadC = new Thread(new C());
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
    public void testSignal(){
        // 创建两个a线程，并等待
        final Thread threadA1 = new Thread(new A());
        final Thread threadA2 = new Thread(new A());
        threadA1.start();
        threadA2.start();

        // 使用b唤醒一个
        final Thread threadB = new Thread(new B());
        // 睡3秒，足够前面两个线程锁住, 代码结果应该是3秒后其中一个a跑完
        threadB.start();
        // result
    }

    class A implements Runnable{

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("Thread a begin " + Thread.currentThread().getName());
                condition.await();
                System.out.println("Thread a end " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }

    class B implements Runnable{

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("Thread b begin " + Thread.currentThread().getName());
                System.out.println("睡3秒");
                Thread.sleep(3000);
                System.out.println("Thread b end " + Thread.currentThread().getName());
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    class C implements Runnable{

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println("Thread b begin " + Thread.currentThread().getName());
                System.out.println("睡3秒");
                Thread.sleep(3000);
                System.out.println("Thread b end " + Thread.currentThread().getName());
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
