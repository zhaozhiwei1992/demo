package com.lx.demo.javaapi.lock;

import org.I0Itec.zkclient.ZkClient;

//将重复代码抽象到子类中（模板方法设计模式）
public abstract class ZookeeperAbstractLock implements ExtLock {
    private static final String CONNECTION = "127.0.0.1:2181";
    protected ZkClient zkClient = new ZkClient(CONNECTION);
    public String lockPath = "/lockPath";

    //获取锁
    @Override
    public void getLock() {
        //1、连接zkClient 创建一个/lock的临时节点
        // 2、 如果节点创建成果，直接执行业务逻辑，如果节点创建失败，进行等待
        if (tryLock()) {
            System.out.println("#####成功获取锁######");
        } else {
            //进行等待
            waitLock();
        }
    }

    //创建失败 进行等待
    abstract void waitLock();


    abstract boolean tryLock();


    //释放锁
    @Override
    public void unLock() {
        //执行完毕 直接连接
        if (zkClient != null) {
            zkClient.close();
            System.out.println("######释放锁完毕######");
        }

    }

}