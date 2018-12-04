package com.lx.cashier.controller;

import com.lx.pay.TransactionPayService;
import com.lx.pay.dto.PaymentNotifyRequest;
import com.lx.pay.dto.PaymentNotifyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 * 处理支付回调
 */
@RestController
@RequestMapping("/pay/")
public class PayController {
    Logger LOG=LoggerFactory.getLogger(PayController.class);

    @Autowired
    TransactionPayService payService;

    @RequestMapping("wechatPayNotify")
    public void weChatPayNotify(HttpServletRequest request , HttpServletResponse response){
        LOG.info("begin - weChatPayNotify method");
        InputStream inputStream=null;
        try {
            inputStream= request.getInputStream();// 从request中取得输入流
            Map<String, Object> wechatResultMap=buildWxParam(inputStream);//从输入流中获取微信回调结果
            PaymentNotifyRequest paymentNotifyRequest=new PaymentNotifyRequest();
            paymentNotifyRequest.setPayChannel("wechat_pay");
            paymentNotifyRequest.setResultMap(wechatResultMap);
            PaymentNotifyResponse notifyResponse=payService.paymentResultNotify(paymentNotifyRequest);
            //告诉服务端消息处理的结果，避免不断的进行结果通知
            response.setContentType("text/xml");
            response.getWriter().print(notifyResponse.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping("alipayNotify")
    public void aliPayNotify(HttpServletRequest request , HttpServletResponse response){
        LOG.info("begin - aliPayNotify method");
        try {
            Map<String, Object> alipayRequestParams = request.getParameterMap();
            PaymentNotifyRequest paymentNotifyRequest=new PaymentNotifyRequest();
            paymentNotifyRequest.setPayChannel("alipay");
            paymentNotifyRequest.setResultMap(alipayRequestParams);
            PaymentNotifyResponse notifyResponse=payService.paymentResultNotify(paymentNotifyRequest);

            //告诉服务端消息处理的结果，避免不断的进行结果通知，此处就是使用的最大化重试的异步通知实现交易结果数据的一致性

            response.getWriter().print(notifyResponse.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Object> buildWxParam(InputStream inputStream) throws IOException {
        // 从输入流读取返回内容
        InputStreamReader inputStreamReader = null;
        inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        // 释放资源
        bufferedReader.close();
        inputStreamReader.close();
        String notify_lines = buffer.toString();
        LOG.info("微信支付回调返回值：" + notify_lines);
        Map<String, Object> paramsMapFromXML = doXMLParse(notify_lines);
        return paramsMapFromXML;
    }

    /**
     * 解析xml字符串转成map集合
     *
     * @param xml
     * @return
     */
    private Map<String, Object> doXMLParse(String xml) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 将编码改为UTF-8,并去掉换行符\空格等
        xml = xml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        //去掉空白 换行符
        final StringBuilder sb = new StringBuilder(xml.length());
        char c;
        for (int i = 0; i < xml.length(); i++) {
            c = xml.charAt(i);
            if (c != '\n' && c != '\r' && c != ' ') {
                sb.append(c);
            }
        }
        xml = sb.toString();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            StringReader reader = new StringReader(xml);
            InputSource inputSource = new InputSource(reader);
            Document document = documentBuilder.parse(inputSource);
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                map.put(node.getNodeName(), node.getFirstChild().getNodeValue());
            }
        } catch (Exception e) {
            LOG.error("xml解析异常：" + e);
        }
        return map;
    }
}
