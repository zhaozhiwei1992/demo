package com.example.springbootdruid.repository;

import com.example.springbootdruid.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @Title: UserRepository
 * @Package com/example/springbootdruid/repository/UserRepository.java
 * @Description: 使用jdbcTemplate持久化数据
 * @author zhaozhiwei
 * @date 2021/7/5 下午9:08
 * @version V1.0
 */
@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int save(User user) {
        return jdbcTemplate.update("INSERT INTO t_user(name, password) values(?, ?)",
                user.getName(), user.getPassword());
    }

    public int update(User user) {
        return jdbcTemplate.update("UPDATE t_user SET name = ? , password = ? WHERE id=?",
                user.getName(), user.getPassword(), user.getId());
    }

    public int delete(long id) {
        return jdbcTemplate.update("DELETE FROM t_user where id = ? ",id);

    }

    public User findById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM t_user WHERE id=?", new Object[] { id }, new BeanPropertyRowMapper<User>(User.class));
    }

    public List<User> findALL() {
        return jdbcTemplate.query("SELECT * FROM t_user", new UserRowMapper());
        // return jdbcTemplate.query("SELECT * FROM t_user", new BeanPropertyRowMapper(User.class));
    }

    class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }

}
