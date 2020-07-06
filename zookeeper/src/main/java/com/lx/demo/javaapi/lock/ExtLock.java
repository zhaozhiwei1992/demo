package com.lx.demo.javaapi.lock;

/**
 * 参考
 * https://www.cnblogs.com/toov5/p/9899489.html
 *
 * 各种问题，详见评论
 */
public interface ExtLock {
   
    //ExtLock基于zk实现分布式锁
    public void  getLock();
    
    //释放锁
    public void unLock();
    
}