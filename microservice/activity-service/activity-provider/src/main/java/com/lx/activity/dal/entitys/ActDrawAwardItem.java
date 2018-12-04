package com.lx.activity.dal.entitys;

import java.io.Serializable;
import java.util.Date;

public class ActDrawAwardItem implements Serializable{
    private static final long serialVersionUID = -2578396302905051481L;
    private Integer id;

    private String itemName;

    private Integer totalNum;

    private Float probability;

    private Integer status;

    private Integer awardId;

    private Integer dayTotalNum;

    private Date createTime;

    private Integer level;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAwardId() {
        return awardId;
    }

    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
    }

    public Integer getDayTotalNum() {
        return dayTotalNum;
    }

    public void setDayTotalNum(Integer dayTotalNum) {
        this.dayTotalNum = dayTotalNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "ActDrawAwardItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", totalNum=" + totalNum +
                ", probability=" + probability +
                ", status=" + status +
                ", awardId=" + awardId +
                ", dayTotalNum=" + dayTotalNum +
                ", createTime=" + createTime +
                ", level=" + level +
                '}';
    }
}