package com.lx.user.dto;

import com.lx.user.abs.AbstractResponse;
import lombok.Data;

/**
 * @author 赵志伟
 * @ClassName: UserQueryResponse
 * @description [描述该类的功能]
 * @create 2018-07-06 下午2:18
 **/
@Data
public class UserQueryResponse extends AbstractResponse{
    private static final long serialVersionUID = -6310387281700615600L;
    private String realName;

    private String avatar;

    private String mobile;

    private String sex;
}
