package com.lx.activity.services.processor;

import com.lx.activity.dal.entitys.ActDrawAward;
import com.lx.activity.dal.entitys.ActDrawAwardItem;
import com.lx.activity.dal.persistence.ActDrawAwardMapper;
import com.lx.activity.services.processor.constants.DrawContants;
import com.lx.activity.services.processor.exception.RewardException;
import com.lx.activity.services.processor.exception.UnRewardException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service("ActivityTurntableDrawProxy")
public class ActivityTurntableDrawProxy implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ActivityTurntableDrawProxy.class);

    // 放大倍数
    private static final int mulriple = 10000;

    @Autowired
    private ActDrawAwardMapper actDrawAwardMapper;

    /**
     * 针对不同奖品类型的处理器
     */
    Map<Integer, RewardProcessor> rewardProcessorMap = new ConcurrentHashMap<Integer, RewardProcessor>();

    @Autowired
    RedisTemplate redisTemplate;

    public ActivityDrawContext doDrawForProxy(ActivityDrawContext activityDrawContext){
        //TODO 检查当前用户剩余抽奖次数
        try {
            List<ActDrawAwardItem> awardItems = (List<ActDrawAwardItem>) redisTemplate.opsForValue().get(DrawContants.DRAW_ITEM);

            ActDrawAwardItem item = doPlay(awardItems);

            ActDrawAward actDrawAward=actDrawAwardMapper.queryAwardById(item.getAwardId());

            activityDrawContext.setActDrawAwardItem(item);

            //奖品的子域   ; //抽奖职能、  奖品发放职能
            rewardProcessorMap.get(actDrawAward.getAwardType()).doReword(activityDrawContext);
        }catch(UnRewardException e){
            logger.info("未抽到奖品或者出现异常的情况下");
        }finally {
            logger.info("ActivityTurntableDrawProxy.doDrawForProxy->{}",activityDrawContext);
        }
        return activityDrawContext;
    }

    /**
     * 抽奖
     *
     * @param awardItems
     * @return
     */
    private ActDrawAwardItem doPlay(List<ActDrawAwardItem> awardItems) {
        ActDrawAwardItem awardItem = null;
        if (awardItems.isEmpty()) {
            throw new RewardException("奖项未设定或未正确初始化");
        }
        int lastScope = 0;
        Collections.shuffle(awardItems);
        Map<Integer, int[]> awardItemScope = new HashMap<Integer, int[]>();
        for (ActDrawAwardItem item : awardItems) { //item.getProbability=0.05 = 5%
            int currentScope = lastScope + new BigDecimal(item.getProbability().floatValue()).multiply(new BigDecimal(mulriple)).intValue();
            awardItemScope.put(item.getId(), new int[]{lastScope + 1, currentScope});
            lastScope = currentScope;
        }
        int luckyNumber = new Random().nextInt(mulriple);
        int luckyPrizeId = 0;
        if (!awardItemScope.isEmpty()) {
            Set<Map.Entry<Integer, int[]>> set = awardItemScope.entrySet();
            for (Map.Entry<Integer, int[]> entry : set)
                if (luckyNumber >= entry.getValue()[0] && luckyNumber <= entry.getValue()[1]) {
                    luckyPrizeId = entry.getKey();
                    break;
                }
        }
        for (ActDrawAwardItem item : awardItems) {
            if (item.getId().intValue() == luckyPrizeId) {
                awardItem = item;
                break;
            }
        }
        return awardItem;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        rewardProcessorMap.put(DrawContants.AWARD_TYPE_ENUM.FINANCE.getValue(), (RewardProcessor) applicationContext.getBean("financeRewardProcessor"));
        rewardProcessorMap.put(DrawContants.AWARD_TYPE_ENUM.JD_CARD.getValue(), (RewardProcessor) applicationContext.getBean("jdCardRewardProcessor"));
        rewardProcessorMap.put(DrawContants.AWARD_TYPE_ENUM.NONE.getValue(), (RewardProcessor) applicationContext.getBean("noneRewardProcessor"));
    }
}
