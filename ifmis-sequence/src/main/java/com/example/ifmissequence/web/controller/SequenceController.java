package com.example.ifmissequence.web.controller;

import com.example.ifmissequence.util.DisLockStoreRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.ifmissequence.web.controller
 * @Description: TODO
 * @date 2021/7/23 上午11:45
 */
@RestController
public class SequenceController {

    private static final Logger logger = LoggerFactory.getLogger(SequenceController.class);

    private static final Random RANDOM = new Random();

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * @data: 2021/7/25-上午12:40
     * @User: zhaozhiwei
     * @method: seq3
      * @param batchid :
     * @return: java.util.List
     * @Description: 自定义分布式锁
     */
    @GetMapping("/seq1/{batchid}")
    public List seq3(@PathVariable String batchid) throws InterruptedException {
        // 线程内部创建的变量才可能保证线程安全
        final List<Map> maps = getMapList();
        Thread.sleep(101 * RANDOM.nextInt(4));

        try (DisLockStoreRedis disLockStoreRedis = new DisLockStoreRedis(redisTemplate, "seq_forupdate", 30)) {
            boolean lock = disLockStoreRedis.getLock();
            if(lock){
                tableLockSeq(maps);

                // 处理后续业务, 业务时间假设不稳定 == 业务时间 + 事务提交时间
                Thread.sleep(1000 * RANDOM.nextInt(4));
                max_int = Integer.parseInt(String.valueOf(maps.get(maps.size()-1).get("billcode")));
                // 手动释放
                disLockStoreRedis.unLock();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 输出结果
        logger.info("第{}批次, 最终数据: {}", batchid, maps);
        return maps;
    }

    private Object lockObj = new Object();
    /**
     * @data: 2021/7/23-上午11:48
     * @User: zhaozhiwei
     * @method: seq1
     * @return: java.lang.String
     * 多个批次处理数据，查看锁是否因并发场景而出现错乱
     */
    @GetMapping("/seq2/{batchid}")
    public List seq2(@PathVariable String batchid) throws InterruptedException {
        // 线程内部创建的变量才可能保证线程安全
        final List<Map> maps = getMapList();
        Thread.sleep(101 * RANDOM.nextInt(4));

        // 锁跟着事务消亡, 单实例下使用synchronized模拟for update
//        这里能锁住，说明请求是通过线程执行，并且dao也为单例对象
        synchronized (lockObj){
            tableLockSeq(maps);

            // 处理后续业务, 业务时间假设不稳定 == 业务时间 + 事务提交时间
            Thread.sleep(1000 * RANDOM.nextInt(4));
            max_int = Integer.parseInt(String.valueOf(maps.get(maps.size()-1).get("billcode")));
        }

        // 输出结果
        logger.info("第{}批次, 最终数据: {}", batchid, maps);
        return maps;
    }

    private List<Map> getMapList() {
        final List<Map> maps = new ArrayList<>();
        final Map<String, Object> map1 = new HashMap<>();
        map1.put("guid", "1");
        final Map<String, Object> map2 = new HashMap<>();
        map2.put("guid", "2");
        maps.add(map1);
//        maps.add(map2);
        return maps;
    }

    /**
     * @param maps :
     * @data: 2021/7/23-下午10:29
     * @User: zhaozhiwei
     * @method: tableLockSeq
     * @return: void
     * @Description: 最大号表方式
     */
    private void tableLockSeq(List<Map> maps) throws InterruptedException {
        // 模拟查询数据库最大值，耗时0-500,
        Thread.sleep(100 * RANDOM.nextInt(5));

        // 获取最大值, 并加1
        int max = max_int + 1;
        for (int i = 0; i < maps.size(); i++) {
            maps.get(i).put("billcode", max + i);
        }
    }

    private int max_int = 500;
}
