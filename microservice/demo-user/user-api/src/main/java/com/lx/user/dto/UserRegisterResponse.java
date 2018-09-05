package com.lx.user.dto;

import com.lx.user.abs.AbstractResponse;
import lombok.Data;

/**
 * @author 赵志伟
 * @ClassName: UserRegisterResponse
 * @description [描述该类的功能]
 * @create 2018-07-10 下午2:21
 **/
@Data
public class UserRegisterResponse extends AbstractResponse{
    private static final long serialVersionUID = -3096897824581672811L;
    private Integer uid;
}
