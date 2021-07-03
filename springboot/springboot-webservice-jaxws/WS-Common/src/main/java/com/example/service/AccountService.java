package com.example.service;

import com.example.domain.Account;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService(serviceName="AccountService",targetNamespace="http://corp.com/",
        name="AccountServiceSoap",portName="AccountServiceSoap")
public interface AccountService {

    @WebMethod
    void insertAccount(Account account);

    @WebMethod
    List<Account> getAccounts(String name);
}