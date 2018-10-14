package com.lx.user.dto;

import com.lx.user.abs.AbstractRequest;

/**
 * @author 赵志伟
 * @ClassName: UserLoginRequest
 * @description [描述该类的功能]
 * @create 2018-07-06 下午4:20
 **/
public class UserLoginRequest extends AbstractRequest{
    private static final long serialVersionUID = 122547013489164419L;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
