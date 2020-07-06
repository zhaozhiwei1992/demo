package com.lx.demo.javaapi.lock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LockDemo implements Runnable{

    private ExtLock lock = new ZookeeperDistrbuteLock();

    /**
     * 获取字符窜
     */
    @Override
    public void run() {
        getNumber();
    }

    //区分不同的订单号
    private static int count = 0;

    //单台服务器，多个线程 同事生成订单号
    public String getNumberStr(){
        try {
            Thread.sleep(500);
        } catch (Exception e) {

        }
        SimpleDateFormat simpt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return simpt.format(new Date()) + "-" + ++count;  //时间戳后面加了 count

    }

    public synchronized void getNumber() { // 加锁 保证线程安全问题 让一个线程操作
        try {
            lock.getLock();
            String number = getNumberStr();
            System.out.println(Thread.currentThread().getName() + ",number" + number);

        } catch (Exception e) {

        } finally {
            lock.unLock();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            // 开启100个线程
            //模拟分布式锁的场景
            new Thread(new LockDemo()).start();
        }
    }
}
