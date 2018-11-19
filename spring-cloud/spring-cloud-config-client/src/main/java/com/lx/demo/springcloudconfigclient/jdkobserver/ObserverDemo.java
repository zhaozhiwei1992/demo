package com.lx.demo.springcloudconfigclient.jdkobserver;

import java.util.Observable;
import java.util.Observer;

/**
 * 主题和订阅可以多对多
 */
public class ObserverDemo {
    public static void main(String[] args) throws InterruptedException {
        //发布者
        Observable observable = new LiuYiFei();

        //订阅者被动获取，被update
        Observer observer = (o, arg) -> System.out.println("我是" + Thread.currentThread().getName() + ", 女神" + arg);
        observable.addObserver(observer);

        ((LiuYiFei) observable).setChanged();
        //该方法中必须是change的才可以走后续流程， 真坑
        observable.notifyObservers("发照片");

        ((LiuYiFei) observable).setChanged();
        observable.notifyObservers("晒美食");
    }

    /**
     * 女神刘亦菲是主题， 被观察者
     */
    static class LiuYiFei extends Observable{

        /**
         * 重写方法， 默认方法受保护无法调用
         */
        @Override
        protected synchronized void setChanged() {
            super.setChanged();
        }
    }
}
