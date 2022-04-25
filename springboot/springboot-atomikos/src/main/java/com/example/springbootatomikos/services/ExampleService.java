package com.example.springbootatomikos.services;

import com.example.springbootatomikos.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootatomikos.services
 * @Description: TODO
 * @date 2022/4/25 下午5:08
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class ExampleService {

    @Autowired
    private PrimaryUserService primaryUserService;

    @Autowired
    private SecondaryUserService secondaryUserService;

    /**
     * @data: 2022/4/25-下午5:19
     * @User: zhaozhiwei
     * @method: testSave

     * @return: void
     * @Description:
     * 全部成功后，会统一提交事务, 输出如下:
     * -04-25 17:15:35.000  INFO 287225 --- [           main] org.hibernate.dialect.Dialect            : HHH000400:
     * Using dialect: org.hibernate.dialect.MySQL5Dialect
     * 2022-04-25 17:15:35.024  INFO 287225 --- [           main] j.LocalContainerEntityManagerFactoryBean :
     * Initialized JPA EntityManagerFactory for persistence unit 'secondaryPersistenceUnit'
     * 2022-04-25 17:15:35.434  INFO 287225 --- [           main] o.h.h.i.QueryTranslatorFactoryInitiator  :
     * HHH000397: Using ASTQueryTranslatorFactory
     * 2022-04-25 17:15:35.666  INFO 287225 --- [           main] o.h.h.i.QueryTranslatorFactoryInitiator  :
     * HHH000397: Using ASTQueryTranslatorFactory
     * 2022-04-25 17:15:36.000  INFO 287225 --- [           main] c.e.s.services.UserServiceTest           : Started
     * UserServiceTest in 4.112 seconds (JVM running for 5.522)
     * 2022-04-25 17:15:36.116  WARN 287225 --- [           main] c.a.icatch.imp.TransactionServiceImp     : Attempt
     * to create a transaction with a timeout that exceeds maximum - truncating to: 300000
     * Hibernate: select user0_.id as id1_0_0_, user0_.age as age2_0_0_, user0_.name as name3_0_0_ from User user0_
     * where user0_.id=?
     * Hibernate: select user0_.id as id1_0_0_, user0_.age as age2_0_0_, user0_.name as name3_0_0_ from User user0_
     * where user0_.id=?
     * ......
     * Hibernate: insert into User (age, name, id) values (?, ?, ?)
     * Hibernate: insert into User (age, name, id) values (?, ?, ?)
     * ......
     * 2022-04-25 17:15:36.910  INFO 287225 --- [       Thread-1] j.LocalContainerEntityManagerFactoryBean : Closing
     * JPA EntityManagerFactory for persistence unit 'secondaryPersistenceUnit'
     * 2022-04-25 17:15:36.912  INFO 287225 --- [       Thread-1] c.a.datasource.pool.ConnectionPool       : atomikos
     * connection pool 'secondary': destroying pool...
     * 2022-04-25 17:15:36.920  INFO 287225 --- [       Thread-1] j.LocalContainerEntityManagerFactoryBean : Closing
     * JPA EntityManagerFactory for persistence unit 'primaryPersistenceUnit'
     * 2022-04-25 17:15:36.921  INFO 287225 --- [       Thread-1] c.a.datasource.pool.ConnectionPool       : atomikos
     * connection pool 'primary': destroying pool...
     */
    public void testSave() {
        for (int i = 0; i < 20; i++) {
            final User user = new User();
            user.setId(i);
            user.setName("zhangsan");
            user.setAge(18);

            primaryUserService.save(user);
            secondaryUserService.save(user);
        }
    }
}
