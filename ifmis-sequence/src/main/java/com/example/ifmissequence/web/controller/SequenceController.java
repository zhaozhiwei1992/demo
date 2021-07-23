package com.example.ifmissequence.web.controller;

import com.example.ifmissequence.domain.CommonDAO;
import com.example.ifmissequence.util.DisLockStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private DisLockStore disLockStore;

    /**
     * @data: 2021/7/23-上午11:48
     * @User: zhaozhiwei
     * @method: seq1
     * @return: java.lang.String
     * 多个批次处理数据，查看锁是否因并发场景而出现错乱
     */
    @GetMapping("/seq2/{batchid}")
    public List seq2(@PathVariable String batchid) throws InterruptedException {
        final List<Map> maps = new ArrayList<>();
        final Map<String, Object> map1 = new HashMap<>();
        map1.put("guid", "1");
        final Map<String, Object> map2 = new HashMap<>();
        map2.put("guid", "2");
        maps.add(map1);
//        maps.add(map2);
        Thread.sleep(101 * RANDOM.nextInt(4));

        // 锁跟着事务消亡, 单实例下使用synchronized模拟for update
//        这里能锁住，说明请求是通过线程执行，并且dao也为单例对象
        synchronized (dao){
            tableLockSeq(maps);

            // 处理后续业务, 业务时间假设不稳定 == 业务时间 + 事务提交时间
            Thread.sleep(1000 * RANDOM.nextInt(4));
            max_int = Integer.parseInt(String.valueOf(maps.get(maps.size()-1).get("billcode")));
        }

        // 输出结果
        logger.info("第{}批次, 最终数据: {}", batchid, maps);
        return maps;
    }

    /**
     * @data: 2021/7/23-上午11:48
     * @User: zhaozhiwei
     * @method: seq1
     * @return: java.lang.String
     * 多个批次处理数据，查看锁是否因并发场景而出现错乱, 不符合
     */
    @GetMapping("/seq1/{batchid}")
    public List seq1(@PathVariable String batchid) throws InterruptedException {
        final List<Map> maps = new ArrayList<>();
        final Map<String, Object> map1 = new HashMap<>();
        map1.put("guid", "1");
        final Map<String, Object> map2 = new HashMap<>();
        map2.put("guid", "2");
        maps.add(map1);
        maps.add(map2);
        Thread.sleep(101 * RANDOM.nextInt(4));

        // 通过分布式锁增加序列, 无法实现可能再事务结束前，最大值已经被读取, 锁得跟着事务走
        disLockSeq(maps);

        // 处理后续业务, 业务时间假设不稳定 == 业务时间 + 事务提交时间
        Thread.sleep(1000 * RANDOM.nextInt(4));
        max_int = Integer.parseInt(String.valueOf(maps.get(1).get("billcode")));

        // 输出结果
        logger.info("第{}批次, 最终数据: {}", batchid, maps);
        return maps;
    }

    @Autowired
    private CommonDAO dao;

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

    /**
     * @param maps
     * @data: 2021/7/23-上午11:50
     * @User: zhaozhiwei
     * @method: seq
     * @return: java.lang.String
     * @Description: 获取最大号, 并发控制取号,
     * 获得号后业务流程过长可能出现未释放
     * 测试: 如果最大号实在锁外持久话，都可能不准确，所以锁得跟着事务走, 用行锁
     */
    public void disLockSeq(List<Map> maps) throws InterruptedException {
        // 5次获取锁机会
        int count = 6;
        for (int j = 0; j < count; j++) {
            //获取锁
            // 使用if 可能出现获取不到编号情况
            if (disLockStore.getLock("agency_code")) {

                // 模拟查询数据库最大值，耗时1000,
                Thread.sleep(100 * RANDOM.nextInt(5));
                // 获取最大值, 并加1
                int max = max_int + 1;

                for (int i = 0; i < maps.size(); i++) {
                    maps.get(i).put("billcode", max + i);
                }
                break;
            } else {
                // 延时
                Thread.sleep(100 * j);
                logger.error("业务繁忙, 延时 {} 获取", 100 * j);
            }
        }
    }
}
