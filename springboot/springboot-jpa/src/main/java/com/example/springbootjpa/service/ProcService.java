package com.example.springbootjpa.service;

import com.example.springbootjpa.domain.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;
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
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("getUserById");
        query.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);
        query.setParameter(1, 1);
        query.execute();
        return (String) query.getOutputParameterValue(2);
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
