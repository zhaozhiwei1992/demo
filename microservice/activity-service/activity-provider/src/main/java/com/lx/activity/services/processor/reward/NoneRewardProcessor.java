package com.lx.activity.services.processor.reward;

import com.lx.activity.services.processor.AbstractRewardProcessor;
import com.lx.activity.services.processor.ActivityDrawContext;
import com.lx.activity.services.processor.constants.DrawContants;
import org.springframework.stereotype.Service;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
@Service("noneRewardProcessor")
public class NoneRewardProcessor extends AbstractRewardProcessor {

    protected void processor(ActivityDrawContext activityDrawContext) {
        logger.info("用户:{},获得奖项:{}",activityDrawContext.getActivityTurntableDrawReq().getUid(),activityDrawContext.getActDrawAwardItem().getItemName());
        modifyAwardRecord(activityDrawContext); //保存记录
    }

    protected void afterProcessor(ActivityDrawContext activityDrawContext) {

    }

    protected int getAwardType() {
        return DrawContants.AWARD_TYPE_ENUM.NONE.getValue();
    }
}
