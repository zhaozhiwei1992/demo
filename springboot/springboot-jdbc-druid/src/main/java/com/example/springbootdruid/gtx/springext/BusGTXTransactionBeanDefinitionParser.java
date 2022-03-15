/**
 * @Title: GTXTransactionBeanDefinitionParser.java
 * @Copyright (C) 2014 龙图软件
 * @Description:
 * @Revision History:
 * @Revision 1.0 2014-8-4  张凯
 */

package com.example.springbootdruid.gtx.springext;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @data: 2021/8/19-下午5:01
 * @User: zhaozhiwei
 * @method:
  * @param null :
 * @return:
 * @Description: 处理xml标签
 */
public class BusGTXTransactionBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {

    /**
     * 初始化bean.
     * <p>
     * Title: doParse
     * </p>
     * <p>
     * Description: 通过代理的方式, 将 transactionService作为被代理bean
     * </p>
     * @param element
     * @param parserContext
     * @param builder
     * @see org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser#doParse(Element,
     *      ParserContext,
     *      BeanDefinitionBuilder)
     */
    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String refbeanid = element.getAttribute("refbeanid");
        // 设置需要代理的beanid
        builder.addPropertyReference("target", refbeanid);
        //增加对非接口的支持
        builder.addPropertyValue("proxyTargetClass", "true");
        builder.setParentName("fasp2TxProxy");
    }

    @Override
    protected Class getBeanClass(Element element) {
        return org.springframework.transaction.interceptor.TransactionProxyFactoryBean.class;
    }

}