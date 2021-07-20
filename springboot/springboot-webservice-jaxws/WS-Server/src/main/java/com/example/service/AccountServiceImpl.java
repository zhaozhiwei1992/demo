package com.example.service;

import com.example.domain.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title: AccountServiceImpl
 * @Package com/example/service/AccountServiceImpl.java
 * @Description: TODO 大佬写点东西
 * 编写服务接口的实现类AccountServiceImpl，此类里面主要编写主要的业务逻辑。
 * 在此类的@WebService注解里面添加了一个属性endpointInterface，表示的是服务接口全路径,
 * 指定为SEI（Service EndPoint Interface）服务端点接口，此处是AccountService所在的全路径。
 * https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#remoting-web-services
 *
 * servicename 作为url一部分，所以可以写export/xx/xx等
 * {@see org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter#calculateEndpointAddress(javax.xml.ws.Endpoint, java.lang.String)}
 * 	protected String calculateEndpointAddress(Endpoint endpoint, String serviceName) {
 * 		String fullAddress = this.baseAddress + serviceName;
 * 		if (endpoint.getClass().getName().startsWith("weblogic.")) {
 * 			// Workaround for WebLogic 10.3
 * 			fullAddress = fullAddress + "/";
 *                }
 * 		return fullAddress;    * 	}
 * @author zhaozhiwei
 * @date 2021/6/29 下午9:45
 * @version V1.0
 */
@Service
@WebService(endpointInterface="com.example.service.AccountService",serviceName="AccountService",targetNamespace="http://soa.example.com/service",name="AccountServiceSoap",portName="AccountServiceSoap")
// 如果只有name, 则在解析时随便写地址都可以访问
//@WebService(name="AccountService")
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @WebMethod
    public List<Account> getAccounts(String name) {
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setName("DreamTech1113");
        accounts.add(account);
        return accounts ;
    }

    @WebMethod
    public void insertAccount(Account account) {
        logger.info("insert successful");
        return;
    }
}