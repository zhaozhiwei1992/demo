package com.lx.demo.persistence;

import com.lx.demo.model.User;
import com.lx.demo.springjdbc.framework.BaseDaoSupport;
import com.lx.demo.springjdbc.framework.QueryRule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.core.common.jdbc.datasource.DynamicDataSource;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDAO extends BaseDaoSupport<User, Integer> {

    @Override
    protected String getPKColumn() {return "id";}

    private JdbcTemplate template;

    private DynamicDataSource dataSource;

    @Resource(name="dynamicDataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = (DynamicDataSource)dataSource;
        this.setDataSourceReadOnly(dataSource);
        this.setDataSourceWrite(dataSource);
    }

//    @Resource(name="readOnlyDataSource")
//	public void setDataSourceReadOnly(DataSource dataSource){
//        super.setDataSourceReadOnly(dataSource);
//    }


//    @Resource(name="writeOnlyDataSource")
//    public void setDataSourceWrite(DataSource dataSource){
//        super.setDataSourceWrite(dataSource);
//    }



//	public List<User> selectByAge(int age) throws  Exception{
//		String sql = "select * from t_user where age = ?";
//		List<User> result = template.query(sql, new RowMapper<User>() {
//
//            public User mapRow(ResultSet rs, int i) throws SQLException {
//
//                //全自动
//                User user = new User();
//                user.setId(rs.getLong("id"));
//                user.setName(rs.getString("name"));
//                user.setAge(rs.getInt("age"));
//                user.setAddr(rs.getString("addr"));
//
//                return user;
//            }
//        }, age);
//
//		return result;
//	}


    /**
     *
     * @param name
     * @return
     */
    public List<User> selectByName(String name) throws Exception{
        //构建一个QureyRule 查询规则
        QueryRule queryRule = QueryRule.getInstance();
        //查询一个name= 赋值 结果，List
        queryRule.andEqual("name", name);
        //相当于自己再拼SQL语句
        return super.select(queryRule);
    }


    public List<User> selectAll() throws Exception{
        QueryRule queryRule = QueryRule.getInstance();
        return super.select(queryRule);
    }

//    /**
//     *
//     */
//    public boolean insert(User entity) throws Exception{
//        if(entity.getAge() >= 30){
//            this.dataSource.getDataSourceEntry().set("db_two");
//        }else{
//            this.dataSource.getDataSourceEntry().set("db_one");
//        }
//
//        Long id = super.insertAndReturnId(entity);
//        entity.setId(id);
//        return id > 0;
//    }


    /**
     * @throws Exception
     *
     */
    public boolean update(User user) throws Exception{
        return super.update(user);
    }

    public boolean delete(User user) throws Exception{
        return super.delete(user);
    }
}
