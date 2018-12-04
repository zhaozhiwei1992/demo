package com.lx.activity.dal.entitys;

import java.io.Serializable;
import java.util.Date;

public class ActDrawAward implements Serializable{

    private static final long serialVersionUID = 1765800142109468438L;

    private Integer id;

    private String awardName;

    private String awardInfo;

    private Integer awardType;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardInfo() {
        return awardInfo;
    }

    public void setAwardInfo(String awardInfo) {
        this.awardInfo = awardInfo;
    }

    public Integer getAwardType() {
        return awardType;
    }

    public void setAwardType(Integer awardType) {
        this.awardType = awardType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ActDrawAward{" +
                "id=" + id +
                ", awardName='" + awardName + '\'' +
                ", awardInfo='" + awardInfo + '\'' +
                ", awardType=" + awardType +
                ", createTime=" + createTime +
                '}';
    }
}