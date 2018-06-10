package com.lx.demo.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 基于zkclient访问zookeeper
 */
public class ZookeeperWithZKClientDemo {
    /**
     * 服务器配置的zkserver， 其中ip地址为各个服务器的ip地址, 2181端口为客户端访问服务器的端口
     */
    private final static String CONNECTSTRING="192.168.140.128:2181,192.168.140.129:2181," +
            "192.168.140.130:2181";

    /**
     * 超时时间
     */
    private static int sessionTimeout=5000;

    public static void main(String[] args) throws InterruptedException {
        //内部已经进行了加锁处理， 保证正常获取连接
        ZkClient zkClient = new ZkClient(CONNECTSTRING, sessionTimeout);

        // 增删改查, 临时节点
//        String s = zkClient.create("/xxtt", "xxx".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        //这个临时节点创建后 客户端session已经结束， 但是发现在其他客户端还可以看到这个节点
        //原因是 服务器还在心跳检测时间内连接重试， 超过时间就没了
        zkClient.createEphemeral("/xxtt");
        //delete
        zkClient.delete("/xxtt");

        //递归创建持久化节点
        zkClient.createPersistent("/xxtt/xx-01/xx-001", true);

        //注册指定节点的修改和删除事件
        zkClient.subscribeDataChanges("/xxtt", new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println(s + " is changed!!");
            }

            public void handleDataDeleted(String s) throws Exception {
                System.out.println(s + " is deleted!!");
            }
        });
        // KeeperErrorCode = Directory not empty for /xxtt
//        zkClient.delete("/xxtt");
        //改
        zkClient.writeData("/xxtt", "i love you");
        //查
        List<String> children = zkClient.getChildren("/xxtt");
        System.out.println(children);
        //递归删除
        zkClient.deleteRecursive("/xxtt");
        TimeUnit.SECONDS.sleep(3);
    }
}
