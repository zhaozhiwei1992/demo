package com.example.springbootlocks.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Date;
import java.util.HashMap;

/**
 * @Title: SingleInsLockStore
 * @Package com/example/springbootlocks/locks/SingleInsLockStore.java
 * @Description: 单实例锁
 * @author zhaozhiwei
 * @date 2021/10/25 上午11:18
 * @version V1.0
 */
public final class SingleInsLockStore {

    private static final Logger logger = LoggerFactory.getLogger(SingleInsLockStore.class);

    private static HashMap lockCache;

    private static String servicetag = System.currentTimeMillis()+"";

    private static AbstractCacheManager cacheManager;

    private static HashMap getLockCache() {
        if (lockCache == null) {
            lockCache = new HashMap<String, Object>();
        }
        return lockCache;
    }

    public static Object get(String key){
        Object value = getLockCache().get(key);
        if(value !=null && !"NULL".equals(value)) {
            return value;
        }else{
            return null;
        }
    }

    public static void put(String key, Object value){
        getLockCache().put(key,value);
    }

    private SingleInsLockStore() {
  
    }
    /** 
     * 根据锁名获取锁 
     *  
     * @param lockName 
     *            锁名 
     * @return 是否锁定成功 
     */  
    public synchronized static Boolean getLock(String lockName) {  
        Boolean locked = false;  
  
        if (lockName == null || lockName.equals("")) {  
            throw new RuntimeException("Lock name can't be empty");  
        }
        Date newdate = new Date();
        Date lockDate = (Date) get(lockName);
        if (lockDate == null) {  
            put(lockName, newdate);
            locked = true;  
        }else if(newdate.getTime() - lockDate.getTime()>60*1000){
            // 超过1分钟，再次执行任务。
            logger.info("超时"+newdate.toLocaleString()+">"+lockDate.toLocaleString()+"="+(newdate.getTime() - lockDate.getTime()));
            put(lockName, newdate);
            locked = true;
        }else{
            logger.info(lockName+"任务执行时间："+newdate.toLocaleString()+">"+lockDate.toLocaleString()+"="+(newdate.getTime() - lockDate.getTime()));
        }
        return locked;  
    }  
  
    /** 
     * 根据锁名释放锁 
     *  
     * @param lockName 
     *            锁名 
     */  
    public synchronized static void releaseLock(String lockName) {  
    	if (lockName == null || lockName.equals("")) {  
            throw new RuntimeException("Lock name can't be empty");  
        }  
        Date lockDate = (Date)get(lockName);
        if (lockDate != null) {  
            //remove(lockName);
            put(lockName, "NULL");
        }  
    }  
  
    /** 
     * 获取上次成功锁定的时间 
     *  
     * @param lockName 
     *            锁名 
     * @return 如果还没有锁定返回NULL 
     */  
    public synchronized static Date getLockDate(String lockName) {  
    	if (lockName == null || lockName.equals("")) {  
            throw new RuntimeException("Lock name can't be empty");  
        }  
        Date lockDate = (Date)get(lockName);
        return lockDate;  
    }

}
