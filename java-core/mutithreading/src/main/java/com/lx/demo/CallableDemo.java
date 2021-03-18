package com.lx.demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 与 Runnable 相比，Callable 可以有返回值，返回值通过 FutureTask 进行封装。
 */
public class CallableDemo implements Callable {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        final CallableDemo callableDemo = new CallableDemo();
        final FutureTask<String> futureTask = new FutureTask<String>(callableDemo);
        final Thread thread = new Thread(futureTask);
        thread.start();
        System.out.println(futureTask.get());
    }

    @Override
    public Object call() throws Exception {
        return "I,m back";
    }
}
