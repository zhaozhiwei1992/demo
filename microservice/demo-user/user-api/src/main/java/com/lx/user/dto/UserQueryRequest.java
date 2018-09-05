package com.lx.user.dto;

import com.lx.user.abs.AbstractRequest;
import lombok.Data;

/**
 * @author 赵志伟
 * @ClassName: UserQueryRequest
 * @description [描述该类的功能]
 * @create 2018-07-06 下午2:18
 **/
@Data
public class UserQueryRequest extends AbstractRequest{
    private static final long serialVersionUID = -653428402852302249L;
    private Integer uid;
}
