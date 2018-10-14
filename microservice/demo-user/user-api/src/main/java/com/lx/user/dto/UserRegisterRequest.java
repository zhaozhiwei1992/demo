package com.lx.user.dto;

import com.lx.user.abs.AbstractRequest;
import lombok.Data;

/**
 * @author 赵志伟
 * @ClassName: UserRegisterRequest
 * @description [描述该类的功能]
 * @create 2018-07-10 下午2:21
 **/
public class UserRegisterRequest extends AbstractRequest{
    private static final long serialVersionUID = 2552281896690897304L;
    private String username;

    private String password;

    private String mobile;

    private String sex;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
