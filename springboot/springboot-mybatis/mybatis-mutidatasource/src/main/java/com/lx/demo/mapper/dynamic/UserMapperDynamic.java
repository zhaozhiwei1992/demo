package com.lx.demo.mapper.dynamic;

import com.lx.demo.mapper.master.UserMapperMaster;
import com.lx.demo.mapper.slaver.UserMapperSlaver;

/**
 * 同时具有master和slaver的能力, 通过内部动态切换数据源，对外无感知
 */
public interface UserMapperDynamic extends UserMapperSlaver, UserMapperMaster {

}
