package com.lx.demo.springbootshiro.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class Role {
    private Long id; // 编号
    final private String name; // 角色标识程序中判断使用,如"admin",这个是唯一的:
    private String description; // 角色描述,UI界面显示使用
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户
    //角色 -- 权限关系：多对多关系;
//    @ManyToMany(fetch= FetchType.EAGER)
    final private List<Permission> permissions;

    // 用户 - 角色关系定义;
//    @ManyToMany
    private List<User> users;// 一个角色对应多个用户

}
