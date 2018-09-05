package com.lx.user.dal.entity;

import java.util.Date;
import lombok.Data;

@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String realname;

    private String avatar;

    private String mobile;

    private String sex;

    private Integer status;

    private Date createTime;
}