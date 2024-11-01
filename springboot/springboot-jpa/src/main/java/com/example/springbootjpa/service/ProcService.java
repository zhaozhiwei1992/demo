package com.example.springbootjpa.service;

import com.example.springbootjpa.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * @Title: ProcService
 * @Package com/example/springbootjpa/service/ProcService.java
 * @Description: 测试存储过程
 * @author zhaozhiwei
 * @date 2024/6/19 下午4:56
 * @version V1.0
 */
@Service
public class ProcService {


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    /**
     * @data: 2024/6/19-下午4:44
     * @User: zhaozhiwei
     * @method: proc

     * @return: java.lang.String
     * @Description: 存储过程测试
     * DELIMITER //
     *
     * CREATE PROCEDURE getUserById(IN userId INT, OUT userName VARCHAR(100))
     * BEGIN
     *     SELECT name INTO userName FROM users WHERE id = userId;
     * END //
     *
     * DELIMITER ;
     *
     * 上述脚本得在mysql控制台执行, dbeaver执行不了
     */
    public String query(){
//        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getUserById");
        final Session session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        final StoredProcedureQuery query = session.createStoredProcedureQuery("getUserById");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);
        query.setParameter(1, 1);
        query.execute();
        return (String) query.getOutputParameterValue(2);
    }

    @Autowired
    private DataSource dataSource;

    public String query2() throws SQLException {
        Connection con = dataSource.getConnection();

        String sql = "{call getUserById(?, ?)}";
        CallableStatement cs = con.prepareCall(sql);
// 设置输入参数
        cs.setInt(1, 1); // 假设我们要查询id为1的员工信息
// 注册输出参数
        cs.registerOutParameter(2, Types.VARCHAR);
// 执行存储过程
        cs.execute();
// 获取输出参数的值
        String name = cs.getString(2);
        System.out.println("Employee Name: " + name);
// 关闭资源
        cs.close();
        return name;
    }

    @Transactional
    public String saveOrUpdate(User user){
        String sql = "select * from users";
        final List resultList = entityManager.createNativeQuery(sql).getResultList();
        System.out.println(resultList);
        return "saveOrUpdate";
    }

    public String merge(User user){
        return "merge";
    }

    public String delete(){
        return "delete";
    }

}
