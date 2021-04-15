package com.lx.demo.springbootmybatis.domain;

import com.lx.demo.springbootmybatis.contrant.SexEnum;

import java.util.Date;

/**
 * @Title: User
 * @Package com/lx/demo/springbootmybatis/domain/User.java
 * @Description: create table t_user
 * (
 *   id          int auto_increment
 *     primary key,
 *   name        varchar(20)                         null,
 *   password    varchar(50)                         null,
 *   realname    varchar(20)                         null,
 *   avatar      varchar(20)                         null,
 *   mobile      varchar(20)                         null,
 *   sex         varchar(5)                          null,
 *   status      int                                 null,
 *   create_time timestamp default CURRENT_TIMESTAMP not null
 *   on update CURRENT_TIMESTAMP,
 *   constraint t_user_id_uindex
 *   unique (id)
 * );
 *
 * org.apache.ibatis.type.TypeHandlerRegistry  mybatis默认定义的TypeHandler
 * @author zhaozhiwei
 * @date 2021/4/15 上午9:06
 * @version V1.0
 */
public class User {
    private Integer id;

    private String name;

    private String password;

    private String realname;

    private String avatar;

    private String mobile;

    private SexEnum sex;

    private Integer status;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex=sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", realname='" + realname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex='" + sex + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}