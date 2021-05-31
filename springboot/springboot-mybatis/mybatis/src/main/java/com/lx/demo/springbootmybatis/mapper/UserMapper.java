package com.lx.demo.springbootmybatis.mapper;

import com.lx.demo.springbootmybatis.domain.User;
import com.lx.demo.springbootmybatis.domain.UserExample;
import java.util.List;

import org.apache.ibatis.annotations.*;

/**
 * 如果想通过spring注入还是需要假如mapper注解或者配置扫描mapperscan
 */
@Mapper
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int batchDeleteByUserId(List<Integer> ids);

    int batchUpdateByUserId(List<User> users);
}