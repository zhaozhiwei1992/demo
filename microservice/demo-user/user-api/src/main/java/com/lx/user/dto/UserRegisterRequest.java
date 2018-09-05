package com.lx.user.dto;

import com.lx.user.abs.AbstractRequest;
import lombok.Data;

/**
 * @author 赵志伟
 * @ClassName: UserRegisterRequest
 * @description [描述该类的功能]
 * @create 2018-07-10 下午2:21
 **/
@Data
public class UserRegisterRequest extends AbstractRequest{
    private static final long serialVersionUID = 2552281896690897304L;
    private String username;

    private String password;

    private String mobile;

    private String sex;
}
