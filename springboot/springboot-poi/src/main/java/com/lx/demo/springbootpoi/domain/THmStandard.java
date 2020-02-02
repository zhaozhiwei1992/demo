package com.lx.demo.springbootpoi.domain;

import com.lx.demo.springbootpoi.annotation.PropertyName;

import java.io.Serializable;

/**
 * (BetpTFamilymember)实体类
 *
 * @author makejava
 * @since 2019-12-04 13:54:13
 */
@PropertyName("家庭成员表")
public class THmStandard extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 131284293549851354L;
    /**
     * 农户编号
     */
    @PropertyName("农户编号")
    private String farmercode;
    /**
     * 户主姓名
     */
    @PropertyName("户主姓名")
    private String name;
    /**
     * 村（居）委会
     */
    @PropertyName("村（居）委会")
    private String villagerid;
    /**
     * 村（居）民小组
     */
    @PropertyName("村（居）民小组")
    private String villagetracts;
    /**
     * 性别
     */
    @PropertyName("性别")
    private String sex;
    /**
     * 年龄
     */
    @PropertyName("年龄")
    private Integer age;
    /**
     * 身份证号
     */
    @PropertyName("身份证号")
    private String citid;
    /**
     * 联系电话
     */
    @PropertyName("联系电话")
    private String telphone;
    /**
     * 银行卡号/存折账号
     */
    private String bankaccount;
    /**
     * 开户银行
     */
    @PropertyName("开户银行")
    private String bank;
    /**
     * 是否低保户
     */
    private String isbigpoverty;
    /**
     * 是否贫困户
     */
    private String ispoverty;
    /**
     * 是否五保户
     */
    private String isinfirm;
    /**
     * 耕地情况
     */
    private String landstate;
    /**
     * 房屋情况
     */
    private String housestate;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private String createtime;
    /**
     * 更新时间
     */
    private String updatetime;
    /**
     * 乡镇
     */
    private String divid;
    /**
     * 财政
     */
    private String province;
    /**
     * 年度
     */
    private Integer year;

    public String getFarmercode() {
        return farmercode;
    }

    public void setFarmercode(String farmercode) {
        this.farmercode = farmercode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVillagerid() {
        return villagerid;
    }

    public void setVillagerid(String villagerid) {
        this.villagerid = villagerid;
    }

    public String getVillagetracts() {
        return villagetracts;
    }

    public void setVillagetracts(String villagetracts) {
        this.villagetracts = villagetracts;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCitid() {
        return citid;
    }

    public void setCitid(String citid) {
        this.citid = citid;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getIsbigpoverty() {
        return isbigpoverty;
    }

    public void setIsbigpoverty(String isbigpoverty) {
        this.isbigpoverty = isbigpoverty;
    }

    public String getIspoverty() {
        return ispoverty;
    }

    public void setIspoverty(String ispoverty) {
        this.ispoverty = ispoverty;
    }

    public String getIsinfirm() {
        return isinfirm;
    }

    public void setIsinfirm(String isinfirm) {
        this.isinfirm = isinfirm;
    }

    public String getLandstate() {
        return landstate;
    }

    public void setLandstate(String landstate) {
        this.landstate = landstate;
    }

    public String getHousestate() {
        return housestate;
    }

    public void setHousestate(String housestate) {
        this.housestate = housestate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getDivid() {
        return divid;
    }

    public void setDivid(String divid) {
        this.divid = divid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}