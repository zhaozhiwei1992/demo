package com.example.listener;

import com.example.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.xml.ws.Endpoint;

/**
 * @Title: PublishWsByEndpoint
 * @Package com/example/listener/PublishWsByEndpoint.java
 * @Description: 手动创建ws的一个入口, 建议直接使用SimpleJaxWsServiceExporter即可，不需要这种实现方式，除非url很特殊
 * 也可以通过下述方式在启动类注册
springApplication.addListeners(new PublishWsByEndpoint());

 * @author zhaozhiwei
 * @date 2021/7/19 下午9:30
 * @version V1.0
 */
@Component
public class PublishWsByEndpoint implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(PublishWsByEndpoint.class);

    private static boolean ispublish = false;

    @Autowired
    private AccountService accountService;

    /**
     * @data: 2021/7/19-下午9:27
     * @User: zhaozhiwei
     * @method: onApplicationEvent
      * @param event :
     * @return: void
     * @Description: 通过javaapi方式开放ws接口
     * {@see org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter#publishEndpoint(javax.xml.ws.Endpoint, javax.xml.ws.WebServiceProvider)}
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

//        如果在application通过addlistener方式初始化，　这里需要手动获取bean, 注入为null, 时间点不同
//        Object accountService = event.getApplicationContext().getBean(AccountService.class);

        try{
            if(!ispublish){
                // 这里配置可以通过配置获取
                Endpoint.publish("http://localhost:8889/custom/AccountService2", accountService);
                logger.info("webservice started on port(s): 8889 (ws)");
                logger.info("webservice测试请求地址: http://localhost:8889/custom/AccountService2?WSDL");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ispublish = true;
        }
    }
}
