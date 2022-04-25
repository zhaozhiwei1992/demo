package com.example.springbootseata.repository;

import com.lx.demo.springbootjdbc.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
@Repository
public class UserRepository {

    private DataSource dataSource;
    private DataSource masterDataSource;
    private DataSource slaveDataSource;

    private final ConcurrentHashMap<Long, User> repository = new ConcurrentHashMap();
    private final AtomicLong idGenerator = new AtomicLong();

    public boolean insert(User record){
//        return this.saveByJDBC(record);
//        return this.saveBySpringJDBC(record) > 0;

        //线程安全
        long id = idGenerator.incrementAndGet();
        return repository.put(id, record) == null;
    }

    /**
     * 返回所有用户
     * 面向 接口提高灵活性
     * @return
     */
    public Collection<User> findAll(){
        return repository.values();
    }

    private JdbcTemplate jdbcTemplate;

    /**
     * 事务管理器
     */
    private final PlatformTransactionManager platformTransactionManager;

    /**
     * 通过springjdbc持久化数据, jdbctemplate默认自动提交
     *
        @Transactional, 这个注解没生效，还得研究下
     * @param record
     * @return
     */
    private int saveBySpringJDBC(User record) {

        // api事务控制
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        // 开始事务
        TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);

        jdbcTemplate.execute("insert into t_user(name) values (?)", (PreparedStatement preparedStatement)->{
            preparedStatement.setString(1, record.getName());
            int i = preparedStatement.executeUpdate();
            return i;
        });

        // 有报错时候事务不能提交
//        if(0<1){
//            throw new NullPointerException();
//        }
        platformTransactionManager.commit(transactionStatus);
        return 0;
    }

    /**
     * 使用构造方法可以自动注入，不需要在@autowrite
     * @param dataSource
     * @param masterDataSource
     * @param slaveDataSource
     * @param jdbcTemplate
     * @param platformTransactionManager
     */
    public UserRepository(DataSource dataSource,
                          @Qualifier("masterDataSource") DataSource masterDataSource,
                          @Qualifier("slaveDataSource") DataSource slaveDataSource,
                          JdbcTemplate jdbcTemplate,
                          PlatformTransactionManager platformTransactionManager) {
        this.dataSource = dataSource;
        this.masterDataSource = masterDataSource;
        this.slaveDataSource = slaveDataSource;
        this.jdbcTemplate = jdbcTemplate;
        this.platformTransactionManager = platformTransactionManager;
    }

    /**
     * 默认情况connection自动提交
     * @param record
     * @return
     */
    public int saveByJDBC(User record){
        try {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("insert into t_user(name) values(?)");
            preparedStatement.setString(1, record.getName());
            int i = preparedStatement.executeUpdate();
            if(i > 0){
                connection.commit();
                return i;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateByPrimaryKeySelective(User record){
        return 0;
    }

    public int updateByPrimaryKey(User record){
        return 0;
    }
}
