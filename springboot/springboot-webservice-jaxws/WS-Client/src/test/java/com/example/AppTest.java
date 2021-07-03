package com.example;

import com.example.config.WsServiceConfiguration;
import com.example.domain.Account;
import com.example.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= WsServiceConfiguration.class)
public class AppTest {
    @Autowired
    private AccountService accountService;

    @Test
    public void testAccount() {
        List<Account> accounts = accountService.getAccounts("name");
        System.out.println(accounts);
    }
}
