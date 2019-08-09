package com.lx.demo.mapper.master;

import com.lx.demo.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author zhaoz
 */
public interface UserMapperMaster{
    @Insert("INSERT INTO user(name) VALUES(#{name})")
    void insert(User user);

    @Update("UPDATE user SET name=#{name} WHERE id =#{id}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id =#{id}")
    void delete(Long id);
}
