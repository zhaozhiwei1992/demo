package com.lx.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 生产者消费者
 * 1.创建一个生产者一个消费者，共有一个容器，
 * 2. 容器满了就停止生产，只要有货就开始消费
 *
 * 涨姿势:
 * 这里一定要注意wait和notify的用法，是针对monitor的，意思现在锁谁脑门上就得用锁对象进行wait或者notify
 */
public class ProducerAndConsumerDemo {

    public static void main(String[] args) {
        final Container container = new Container();

        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        final Future<?> submit = executorService.submit(() -> {
            final Producer producer = new Producer(container);
            producer.push();
//            container.producer();
        });

        final Future<?> submit1 = executorService.submit(() -> {
            final Consumer consumer = new Consumer(container);
            consumer.pull();
//            container.consumer();
        });

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

}

/**
 * 容器
 */
class Container{

    public static final int MAXSIZE = 5;

    public final List<Integer> data = new ArrayList();

    public Container() {
    }

    public boolean isNotFull() {
        return data.size() <= MAXSIZE;
    }

    public void push(int i) {
        data.add(i);
    }

    public boolean isNotEmpty() {
        return !data.isEmpty();
    }

    public Integer pull() {
        return data.remove(0);
    }

    Random random = new Random();

    public void producer(){
        while (true){
            synchronized (this){
                while (this.isNotFull()){
                    int value = random.nextInt(100);
                    System.out.printf("线程[%s] 正在生产数据 : %d\n", Thread.currentThread().getName(), value);
                    this.push(value);
                    notify();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 满了就等待消费
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void consumer(){
        while (true){
            synchronized (this){
                while (this.isNotEmpty()){
                    final Integer value = this.pull();
                    System.out.printf("线程[%s] 正在消费数据 : %d\n", Thread.currentThread().getName(), value);
                    notify();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //空了就等待
                try {
                    // 默认就是this
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

/**
 * 生产者
 */
class Producer{

    private Container container;

    public Producer(Container container) {
        this.container = container;
    }

    private Random random = new Random();

    /**
     * 生产
     */
    public void push(){
        while (true){
            synchronized (container){
                while (container.isNotFull()){
                    int value = random.nextInt(100);
                    System.out.printf("线程[%s] 正在生产数据 : %d\n", Thread.currentThread().getName(), value);
                    container.push(value);
                    container.notify();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // 满了就等待消费
                try {
                    container.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/**
 * 消费者
 */
class Consumer{
    private Container container;

    public Consumer(Container container) {
        this.container = container;
    }

    public void pull(){
        while (true){
            synchronized (container){

                while (container.isNotEmpty()){
                    final Integer value = container.pull();
                    System.out.printf("线程[%s] 正在消费数据 : %d\n", Thread.currentThread().getName(), value);
                    // 这里一定要用锁对象来调用
                    //Wakes up a single thread that is waiting on this object's
                    //     * monitor. If any threads are waiting on this object, one of them
                    //     * is chosen to be awakened
                    container.notify();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                //空了就等待
                try {
                    // 这里一定要用锁对象来调用
                    container.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
