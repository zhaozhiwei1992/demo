package com.lx.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 基于curator实现zookeeper 常用操作
 */
public class ZookeeperWithCuratorDemo {
    /**
     * 服务器配置的zkserver， 其中ip地址为各个服务器的ip地址, 2181端口为客户端访问服务器的端口
     */
    private final static String CONNECTSTRING="192.168.140.128:2181,192.168.140.129:2181," +
            "192.168.140.130:2181";

    /**
     * session超时时间
     */
    private static int sessionTimeout=5000;

    public static void main(String[] args) throws Exception {
        //建立连接 俩种方式
        //normal
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(CONNECTSTRING, sessionTimeout,
                5000, new ExponentialBackoffRetry(1000, 3));
        //curator的方式都需要start以后才会启动
        curatorFramework.start();

        //fluent 链式风格
        final CuratorFramework zkWithCuratorBuild = CuratorFrameworkFactory
                .builder()
                .connectString(CONNECTSTRING)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();
        zkWithCuratorBuild.start();

        // 增删改查 同步操作
        //递归创建(creatingParentContainersIfNeeded)持久化节点
        String s = zkWithCuratorBuild.create()
                .creatingParentContainersIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath("/xxtt/tt/tt-01", "123".getBytes());
        System.out.println(s);
        //改
        Stat stat = zkWithCuratorBuild.setData().forPath("/xxtt", "456".getBytes());
        System.out.println(stat);
        //查
        Stat stat1 = new Stat();
        byte[] bytes = zkWithCuratorBuild.getData().storingStatIn(stat1).forPath("/xxtt");
        System.out.println(bytes + " stat: " + stat1);
        //删除 (.deletingChildrenIfNeeded 递归删除)
        zkWithCuratorBuild.delete().deletingChildrenIfNeeded().forPath("/xxtt");
//        TimeUnit.SECONDS.sleep(3);

        //异步操作
        zkWithCuratorBuild.create().creatingParentContainersIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .inBackground(new BackgroundCallback() {
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        System.out.println("节点被创建: name: " + curatorEvent.getPath() + " type:" + curatorEvent.getType());

                        zkWithCuratorBuild.delete().deletingChildrenIfNeeded().forPath("/xxtt");
                        System.out.println("节点删掉了");
                    }
                }).forPath("/xxtt", "987".getBytes());
        //给异步操作一个缓冲事件 实现方式很多{system.in.read();  countlaunch ....}
        TimeUnit.SECONDS.sleep(3);

        // 事务操作
        Collection<CuratorTransactionResult> curatorTransactionResults = zkWithCuratorBuild.inTransaction().create().forPath("/xxtt", "111".getBytes())
                .and()
                .setData().forPath("/tt", "2222".getBytes())
                .and()
                .commit();
        for (CuratorTransactionResult curatorTransactionResult : curatorTransactionResults) {
            System.out.println(curatorTransactionResult.getForPath() + " ---->> " + curatorTransactionResult.getType());
        }
        zkWithCuratorBuild.delete().deletingChildrenIfNeeded().forPath("/xxtt");

        // 事件监听  nodecache  pathcache treecache
//        NodeCache nodeCache = new NodeCache(curatorFramework, "/xxtt");
//        nodeCache.start();
//        nodeCache.getListenable().addListener(()-> System.out.println("NodeCache: 节点发生了变化-------"));
//
//        curatorFramework.create().forPath("/xxtt", "111".getBytes());
//        curatorFramework.setData().forPath("/xxtt", "6666".getBytes());
//        curatorFramework.delete().forPath("/xxtt");
//        TimeUnit.SECONDS.sleep(3);

        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, "/xxtt", true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable().addListener((curatorFramework1, pathChildrenCacheEvent) -> {
            switch (pathChildrenCacheEvent.getType()) {
                case CHILD_ADDED:
                    System.out.println("pathchildrencache:  增加子节点------ ");
                    break;
                case CHILD_UPDATED:
                    System.out.println("pathchildrencache:  修改子节点------");
                    break;
                case CHILD_REMOVED:
                    System.out.println("pathchildrencache:  删除子节点------");
                    break;
                case CONNECTION_SUSPENDED:
                    break;
                case CONNECTION_RECONNECTED:
                    break;
                case CONNECTION_LOST:
                    break;
                case INITIALIZED:
                    break;
            }
        });

        curatorFramework.create().forPath("/xxtt", "111".getBytes());
        curatorFramework.setData().forPath("/xxtt", "6666".getBytes());
        curatorFramework.create().forPath("/xxtt/tt", "6666".getBytes());
        curatorFramework.setData().forPath("/xxtt/tt", "9999".getBytes());
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/xxtt");
        TimeUnit.SECONDS.sleep(5);
    }
}
