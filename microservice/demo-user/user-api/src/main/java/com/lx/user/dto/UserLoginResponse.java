package com.lx.user.dto;

import com.lx.user.abs.AbstractResponse;
import lombok.Data;

/**
 * @author 赵志伟
 * @ClassName: UserLoginResponse
 * @description [描述该类的功能]
 * @create 2018-07-06 下午4:20
 **/
public class UserLoginResponse extends AbstractResponse{
    private static final long serialVersionUID = 8837620432368198051L;

    /**
     * 用户id
     */
    private Integer uid;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 手机号
     */
    private String mobile;

    /**
     *
     */
    private String token;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
