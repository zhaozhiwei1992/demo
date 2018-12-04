package com.lx.activity.services.processor;

import com.lx.activity.dal.entitys.ActDrawAward;
import com.lx.activity.dal.entitys.ActDrawAwardItem;
import com.lx.activity.draw.bean.ActivityTurntableDrawReq;
import com.lx.user.dto.UserQueryResponse;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ActivityDrawContext {

    private ActivityTurntableDrawReq activityTurntableDrawReq;

    private ActDrawAwardItem actDrawAwardItem;

    private ActDrawAward actDrawAward;

    private UserQueryResponse currentUser;

    public ActivityTurntableDrawReq getActivityTurntableDrawReq() {
        return activityTurntableDrawReq;
    }

    public void setActivityTurntableDrawReq(ActivityTurntableDrawReq activityTurntableDrawReq) {
        this.activityTurntableDrawReq = activityTurntableDrawReq;
    }

    public ActDrawAwardItem getActDrawAwardItem() {
        return actDrawAwardItem;
    }

    public void setActDrawAwardItem(ActDrawAwardItem actDrawAwardItem) {
        this.actDrawAwardItem = actDrawAwardItem;
    }

    public UserQueryResponse getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(UserQueryResponse currentUser) {
        this.currentUser = currentUser;
    }

    public ActDrawAward getActDrawAward() {
        return actDrawAward;
    }

    public void setActDrawAward(ActDrawAward actDrawAward) {
        this.actDrawAward = actDrawAward;
    }
}
