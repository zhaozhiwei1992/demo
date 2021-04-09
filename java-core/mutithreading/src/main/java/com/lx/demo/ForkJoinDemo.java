package com.lx.demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: *****多线程计算1 到100之和
 * @date 2021/3/22 上午11:42
 */
public class ForkJoinDemo extends RecursiveTask<Integer> {

    //计算阈 值
    private int threshold = 5;

    // 左右边界
    private int first;
    private int last;

    public ForkJoinDemo(int first, int last) {
        this.first = first;
        this.last = last;
    }
    
    /**
     * @data: 2021/3/22-上午11:59
     * @User: zhaozhiwei
     * @method: compute

     * @return: java.lang.Integer
     * @Description: 分组计算数据
     */
    @Override
    protected Integer compute() {
        int result = 0;

        if(last - first <= threshold){
            // 小任务累加
            for (int i = first; i <= last; i++) {
                result += i;
            }
        }else{
            // 任务分解
            // 中位数
            int middle = first + (last-first)/2;
            final ForkJoinDemo leftForkJoinTask = new ForkJoinDemo(first, middle);
            final ForkJoinDemo rightForkJoinTask = new ForkJoinDemo(middle+1, last);
            leftForkJoinTask.fork();
            rightForkJoinTask.fork();
            result = leftForkJoinTask.join() + rightForkJoinTask.join();
        }
        return result;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ForkJoinDemo forkJoinDemo = new ForkJoinDemo(1, 100);
        final ForkJoinPool forkJoinPool = new ForkJoinPool();
        final ForkJoinTask<Integer> submit = forkJoinPool.submit(forkJoinDemo);
        // 5050
        System.out.println(submit.get());
    }

}
