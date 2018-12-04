package com.lx.activity.draw.bean;

import java.io.Serializable;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class ActivityTurntableDrawReq implements Serializable{
    private static final long serialVersionUID = 5362481131786992982L;

    private Integer uid;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "ActivityTurntableDrawReq{" +
                "uid=" + uid +
                '}';
    }
}
