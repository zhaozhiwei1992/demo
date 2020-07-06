package com.lx.demo.javaapi.lock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZookeeperDistrbuteLock extends ZookeeperAbstractLock {

    @Override
    boolean tryLock() {
        try {
//            创建临时节点
            zkClient.createEphemeral(lockPath);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    void waitLock() {

        CountDownLatch countDownLatch = null;
        CountDownLatch finalCountDownLatch = countDownLatch;
        IZkDataListener iZkDataListener = new IZkDataListener() {

            // 节点被删除
            @Override
            public void handleDataDeleted(String arg0) throws Exception {
                if (finalCountDownLatch != null && tryLock()) {
                    finalCountDownLatch.countDown();
                }
            }

            // 节点被修改
            @Override
            public void handleDataChange(String arg0, Object arg1) throws Exception {

            }
        };

        // 监听事件通知
        zkClient.subscribeDataChanges(lockPath, iZkDataListener);
        // 控制程序的等待
        if (zkClient.exists(lockPath)) {  //如果 检查出 已经被创建了 就new 然后进行等待
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await(); //等待时候 就不往下走了   当为0 时候 后面的继续执行
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        //后面代码继续执行
        //为了不影响程序的执行 建议删除该事件监听 监听完了就删除掉
        zkClient.unsubscribeDataChanges(lockPath, iZkDataListener);

    }

}