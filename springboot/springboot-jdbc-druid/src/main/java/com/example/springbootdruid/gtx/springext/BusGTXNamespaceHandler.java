/**
 * @Title: GTXNamespaceHandler.java
 * @Copyright (C) 2014 龙图软件
 * @Description:
 * @Revision History:
 * @Revision 1.0 2014-8-4  张凯
 */

package com.example.springbootdruid.gtx.springext;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Title: BusGTXNamespaceHandler
 * @Package com/example/springbootdruid/gtx/springext/BusGTXNamespaceHandler.java
 * @Description: 全局事务设置
 * @author zhaozhiwei
 * @date 2021/8/19 下午4:41
 * @version V1.0
 */
public class BusGTXNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        //注册datasourceid标签
//        registerBeanDefinitionParser("datasourceid", new BusGTXDataSourceIdBeanDefinitionParser());
        //注册transaction标签
        registerBeanDefinitionParser("transaction", new BusGTXTransactionBeanDefinitionParser());
    }

}
