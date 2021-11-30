package com.lx.demo.springbootscheduler.javacore;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class TestTimer {
    public static void main(String[] args) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                log.info("task  run: {}", new Date());
                new TestTimer().retryTest(1000, Arrays.asList(new HashMap()));
            }
        };
        Timer timer = new Timer();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。这里是每3秒执行一次
        timer.schedule(timerTask, 10, 3000);
    }

    /**
     * @return: boolean
     * @Description:
     * 1. 测试一批量数据，支持异常后重试
     * , 每次处理pageCount条, 全部数据是datas
     * 2. datas数据条数可能超过了1000
     */
    public boolean retryTest(int pageCount, List<Map> datas) {

//      一批数据, 起始默认1000条, 遇到异常就减半处理
        log.info("本次处理每次 {}条", pageCount);

//            datas分页处理, 事务内部处理分页
        final Map<String, List> paging = paging(datas, pageCount);
        for (Map.Entry<String, List> stringListEntry : paging.entrySet()) {
            final List value = stringListEntry.getValue();
            try {
//              业务bo(带事务处理),
                // 业务处理开始
                final int i = new Random().nextInt(2);
                if(i > 0){
                    log.info("随机数，模拟业务抛出异常, {}", i);
                    throw new RuntimeException("抛出异常");
                }
                // 业务处理结束, 不抛出异常则表示业务处理成功
            } catch (Exception e) {
//          处理失败, 则处理一半
                log.info("本次处理每次 {} 条失败, 减少条数, 重新处理本次业务数据", pageCount);
                retryTest(pageCount / 2, value);
            }
        }

        return true;
    }

    public static Map<String, List> paging(List list, int perPageNum)
    {

        Map<String, List> map = new HashMap<String, List>();
        // 分组处理数据
        int allcount = list.size();
        int pagecount;
        int pagesize = allcount % perPageNum;
        if (pagesize > 0) {
            pagecount = allcount / perPageNum + 1;
        }
        else {
            pagecount = allcount / perPageNum;
        }
        for (int j = 1; j <= pagecount; j++) {
            List sublist;
            if (pagesize == 0) {
                sublist = list.subList((j - 1) * perPageNum, perPageNum * j);
            }
            else {
                if (j == pagecount) {
                    sublist = list.subList((j - 1) * perPageNum, allcount);
                }
                else {
                    sublist = list.subList((j - 1) * perPageNum, perPageNum * j);
                }
            }
            map.put(String.valueOf(j), sublist);
        }
        return map;
    }

}
