package com.lx.demo.mapper;

import com.lx.demo.domain.User;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zhaoz
 */
public interface UserMapper extends Mapper<User> {
}
