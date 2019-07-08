package com.lx.demo.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * 搭建集群环境，　参考笔记: https://github.com/microzhao/notes/blob/master/%E7%BC%96%E7%A8%8B%E8%AF%AD%E8%A8%80/java/%E5%88%86%E5%B8%83%E5%BC%8F/zookeeper.org
 */
public class ZookeeperClient {

    /**
     * 服务器配置的zkserver， 其中ip地址为各个服务器的ip地址, 2181端口为客户端访问服务器的端口
     */
    private final static String CONNECTSTRING="127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    /**
     * 超时时间
     */
    private static int sessionTimeout=5000;

    /**
     * 获取连接
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static ZooKeeper getInstance() throws IOException, InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
//        ZooKeeper zooKeeper=new ZooKeeper(CONNECTSTRING, sessionTimeout, new Watcher() {
//            public void process(WatchedEvent event) {
//                if(event.getState()== Event.KeeperState.SyncConnected){
//                    switch (event.getType()) {
//                        case None:
//                            //第一次进入
//                            System.out.println("连接成功---------" + event);
//                            //使用countDown保证连接成功，连接也需要时间, 只有判断连接上了， 才会释放锁
//                            conectStatus.countDown();
//                            break;
//                    }
//                }
//            }
//        });
        ZooKeeper zooKeeper=new ZooKeeper(CONNECTSTRING, sessionTimeout,new ZookeeperWatcher(countDownLatch));
        countDownLatch.await();
        return zooKeeper;
    }

    public static int getSessionTimeout() {
        return sessionTimeout;
    }

    /**
     * 这里做为全局变量主要是为了传给watcher， 使得watcher可以调用getdata，或者exists，
     * 从而让下次增删改查事件可以被监听
     */
    private static ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = getInstance();

        // 增删改查, 临时节点
        zooKeeper.create("/xxtt", "xxx".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //注册对这个节点的监听事件
        zooKeeper.getData("/xxtt", new ZookeeperWatcher(), new Stat());

        zooKeeper.setData("/xxtt", "i love you".getBytes(), -1);
        //delete 和create 临时节点没有触发watch事件
        zooKeeper.delete("/xxtt", -1);

        // 增删改查, 持久化节点
        //执行下面这条语句， 节点创建时才可以触发事件
        Stat exists = zooKeeper.exists("/tt", true);
//        Stat exists = null;
        if(exists == null){
            zooKeeper.create("/tt", "xxx".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            //这里又写了一次下面语句才出发事件, 也可以将下面的语句写入到watcher中， 触发后创建事件后就执行下面语句
            //同样达到相同的效果
//            zooKeeper.exists("/tt/tt2", true);
            zooKeeper.setData("/tt", "xx00".getBytes(), -1);
//            zooKeeper.exists("/tt/tt2", true);
            zooKeeper.delete("/tt", -1);
        }else{
            zooKeeper.delete("/tt", -1);
        }
    }

    /**
     * 单独抽取zookeeper使用的watcher实现， 保证对节点其他操作也可以被观察到
     * 如果使用匿名创建就只能检测连接操作
     */
    public static class ZookeeperWatcher implements Watcher {

        private CountDownLatch countDownLatch;

        public ZookeeperWatcher(){

        }

        public ZookeeperWatcher(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public void process(WatchedEvent watchedEvent) {
            if(watchedEvent.getState()== Event.KeeperState.SyncConnected){
                switch (watchedEvent.getType()) {
                    case None:
                        //第一次进入
                        System.out.println("连接成功---------" + watchedEvent);
                        //使用countDown保证连接成功，连接也需要时间, 只有判断连接上了， 才会释放锁
                        countDownLatch.countDown();
                        break;
                    case NodeCreated:
                        System.out.println("节点创建成功---------" + watchedEvent);
                        try {
                            zooKeeper.getData(watchedEvent.getPath(), true, new Stat());
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case NodeDeleted:
                        System.out.println("节点删除成功---------" + watchedEvent);
                        break;
                    case NodeDataChanged:
                        System.out.println("节点修改成功---------" + watchedEvent);
                        try {
                            zooKeeper.exists(watchedEvent.getPath(), true);
                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case NodeChildrenChanged:
                        System.out.println("子节点修改成功---------" + watchedEvent);
                        break;
                }
            }
        }
    }
}
