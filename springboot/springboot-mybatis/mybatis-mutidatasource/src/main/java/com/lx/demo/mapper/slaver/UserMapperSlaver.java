package com.lx.demo.mapper.slaver;

import com.lx.demo.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhaoz
 */
public interface UserMapperSlaver{

    @Select("SELECT * FROM user")
    List<User> getAll();

    @Select("SELECT * FROM user WHERE id = #{id}")
    User getOne(Long id);

}
