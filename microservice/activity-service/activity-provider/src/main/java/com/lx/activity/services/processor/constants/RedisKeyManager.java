package com.lx.activity.services.processor.constants;


import com.lx.activity.dal.entitys.ActDrawAward;
import com.lx.activity.draw.bean.ActivityTurntableDrawReq;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class RedisKeyManager {

    /**
     * 正在抽奖的key
     * @param activityTurntableDrawReq
     * @return
     */
    public static String getDrawingRedisKey(ActivityTurntableDrawReq activityTurntableDrawReq) {
        return DrawContants.DRAWING_PREFIX+String.valueOf(activityTurntableDrawReq.getUid());
    }

    public static String getAwardRedisKey(ActDrawAward actDrawAward){
        return DrawContants.DRAW_AWARD+actDrawAward.getAwardType()+":"+actDrawAward.getId();
    }
}
