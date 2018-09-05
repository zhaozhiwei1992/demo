package com.lx.user.dto;

import com.lx.user.abs.AbstractRequest;
import lombok.Data;

/**
 * @author 赵志伟
 * @ClassName: UserLoginRequest
 * @description [描述该类的功能]
 * @create 2018-07-06 下午4:20
 **/
@Data
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
}
