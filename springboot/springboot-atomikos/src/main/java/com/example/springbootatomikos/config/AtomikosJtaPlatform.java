package com.example.springbootatomikos.config;

import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * @Title: AtomikosJtaPlatform
 * @Package com/example/springbootatomikos/config/AtomikosJtaPlatform.java
 * @Description: TODO 大佬写点东西
 * @author zhaozhiwei
 * @date 2022/4/25 下午4:45
 * @version V1.0
 */
public class AtomikosJtaPlatform extends AbstractJtaPlatform {
    private static final long serialVersionUID = 1L;

    static TransactionManager transactionManager;
    static UserTransaction transaction;

    @Override
    protected TransactionManager locateTransactionManager() {
        return transactionManager;
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return transaction;
    }
}