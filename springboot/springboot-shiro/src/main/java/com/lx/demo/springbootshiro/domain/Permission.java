package com.lx.demo.springbootshiro.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 功能权限
 * @Data 包含了 @ToString、@EqualsAndHashCode、@Getter / @Setter和@RequiredArgsConstructor的功能
 * @RequiredArgsConstructor会生成构造方法（可能带参数也可能不带参数），如果带参数，这参数只能是以final修饰的未经初始化的字段，或者是以@NonNull注解的未经初始化的字段
 * @RequiredArgsConstructor(staticName = "of")会生成一个of()的静态方法，并把构造方法设置为私有的
 *
 * 链接：https://www.jianshu.com/p/365ea41b3573
 */
@Data
public class Permission {
    private Long id;
    private String name;//名称.
//    @Column(columnDefinition="enum('menu','button')")
    private String resourceType;//资源类型，[menu|button]
    private String url;//资源路径.
    final private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private Boolean available = Boolean.FALSE;

//    @ManyToMany
    private List<Role> roles;
}
