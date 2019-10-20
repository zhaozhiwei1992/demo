package com.lx.demo.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Description 测试一些原子操作　普通操作再多线程下得执行情况
 * 结论: 如果线程数小与执行次数时，对与非原子操作，结果有不确定性
 * @auther lx7ly
 * @create 2019-10-20 下午4:59
 */
public class AtomicOperator {

    public static void main(String[] args) {
        //测试多线程(100)下对数据得累加
        //如果线程数小于累加次数时，非原子操作可能累加结果不同
        final ExecutorService thread100 = Executors.newFixedThreadPool(100);
        final User user = new User();
        for (int i = 0; i < 1000; i++) {
            thread100.execute(() -> {
                user.idIncrement();
            });
        }
        //多线程下的id值 717
        //多线程下的atomId值 1000
        System.out.printf("多线程下的id值 %s\n", user.getId());
        System.out.printf("多线程下的atomId值 %s\n", user.getAtomID());
        thread100.shutdown();
    }

    static class User{
        private long id = 0;
        private AtomicLong atomID = new AtomicLong(0);

        public void idIncrement(){
            id++;
            atomID.addAndGet(1L);
        }

        public long getId() {
            return id;
        }

        public AtomicLong getAtomID() {
            return atomID;
        }
    }
}
