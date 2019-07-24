package com.lx.demo.springbootmybatis.mapper;

import com.lx.demo.springbootmybatis.domain.User;
import com.lx.demo.springbootmybatis.handler.StringTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * 注解方式需要@Mapper
 */
@Mapper
public interface UserMapperAnnotation {

    /**
     * 1. 可以不用再搞xml
     * 2. 缺点： 不利于sql调优
     *
     *
     <result column="name" jdbcType="VARCHAR" property="name" />
     <result column="password" jdbcType="VARCHAR" property="password" />
     <result column="realname" jdbcType="VARCHAR" property="realname" />
     <result column="avatar" jdbcType="VARCHAR" property="avatar" />
     <result column="mobile" jdbcType="VARCHAR" property="mobile" />
     <result column="sex" jdbcType="VARCHAR" property="sex" />
     <result column="status" jdbcType="INTEGER" property="status" />
     <result column="create_time" jdbcType="TIMESTAMP" property="createTime" />
     * @param id
     * @return
     */
    @Results(value = {
            @Result(column="name",jdbcType =JdbcType.VARCHAR,property="name"),
            @Result(column="password",jdbcType=JdbcType.VARCHAR,property="password", typeHandler = StringTypeHandler.class),
            @Result(column="realname",jdbcType=JdbcType.VARCHAR,property="realname")
    })
    @Select("select name, password, realname from t_user where id = #{id}")
    User selectByID(Integer id);
}