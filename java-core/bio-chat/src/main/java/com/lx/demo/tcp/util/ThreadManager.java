package com.lx.demo.tcp.util;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理的工具类，封装类
 * 线程池的管理 ，通过java 中的api实现管理
 *
 */
public class ThreadManager {
    //通过ThreadPoolExecutor的代理类来对线程池的管理
    private static ThreadPollProxy mThreadPollProxy;
    //单列对象
    public static ThreadPollProxy getThreadPollProxy(){
        synchronized (ThreadPollProxy.class) {
            if(mThreadPollProxy==null){
                mThreadPollProxy=new ThreadPollProxy(4,6,1);
            }
        }
        return mThreadPollProxy;
    }
    //通过ThreadPoolExecutor的代理类来对线程池的管理
    public static class ThreadPollProxy{
        private ThreadPoolExecutor poolExecutor;//线程池执行者 ，java内部通过该api实现对线程池管理

        /**
         * 线程池的基本大小
         */
        private int corePoolSize;

        /**
         * 线程池最大数量
         */
        private int maximumPoolSize;

        /**
         * 线程活动保持时间
         */
        private long keepAliveTime;

        public ThreadPollProxy(int corePoolSize,int maximumPoolSize,long keepAliveTime){
            this.corePoolSize=corePoolSize;
            this.maximumPoolSize=maximumPoolSize;
            this.keepAliveTime=keepAliveTime;
        }
        //对外提供一个执行任务的方法
        public void execute(Runnable r){
            if(poolExecutor==null||poolExecutor.isShutdown()){
                poolExecutor=new ThreadPoolExecutor(
                        //核心线程数量
                        corePoolSize,
                        //最大线程数量
                        maximumPoolSize,
                        //当线程空闲时，保持活跃的时间
                        keepAliveTime,
                        //时间单元 ，毫秒级
                        TimeUnit.MILLISECONDS,
                        //线程任务队列
                        new LinkedBlockingQueue<Runnable>(),
                        //创建线程的工厂
                        Executors.defaultThreadFactory());
            }
            poolExecutor.execute(r);
        }
    }
}
