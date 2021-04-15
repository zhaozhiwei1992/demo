package com.lx.demo.springbootmybatis.handler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @Title: DateTypeHandler
 * @Package com/lx/demo/springbootmybatis/handler/DateTypeHandler.java
 * @Description: mysql加载数据转换为java Date类型
 * @author zhaozhiwei
 * @date 2021/4/14 下午7:36
 * @version V1.0
 */
@MappedJdbcTypes(JdbcType.BIGINT)
@MappedTypes(Date.class)
public class DateTypeHandler implements TypeHandler<Date> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
        long time = parameter.getTime();
        ps.setLong(i, time);
    }

    @Override
    public Date getResult(ResultSet rs, String columnName) throws SQLException {
        System.out.printf("com.lx.demo.springbootmybatis.handler.DateTypeHandler: " + columnName);
        long time = rs.getLong("createTime");
        return new Date(time);
    }

    @Override
    public Date getResult(ResultSet rs, int columnIndex) throws SQLException {
        long time = rs.getLong(columnIndex);
        return new Date(time);
    }

    @Override
    public Date getResult(CallableStatement cs, int columnIndex) throws SQLException {
        long time = cs.getLong(columnIndex);
        return new Date(time);
    }
}
