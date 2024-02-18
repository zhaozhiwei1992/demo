package com.lx.demo;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.StringWriter;
import java.util.*;

/**
 * @Title: XmlUtil
 * @Package com/lx/demo/XmlUtil.java
 * @Description: 测试了了下, 这玩意儿JaxB用map, 里边东西丢掉了, 坑
 * 直接用dom4j或者自己拼个格式, hutool的xmlutil也可以用
 * @author zhaozhiwei
 * @date 2023/6/19 下午7:13
 * @version V1.0
 */
public class XmlUtil {

    public static void main(String[] args) throws JAXBException {
        testExport();
    }

    private static void testExport() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(MyClass.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        List<LinkedHashMap<String, String>> myList = new ArrayList<>();
        LinkedHashMap<String, String> map1 = new LinkedHashMap<>();
        map1.put("name", "John");
        map1.put("age", "30");
        myList.add(map1);
        final MyClass myClass = new MyClass();
        myClass.setMyList(myList);
        final StringWriter stringWriter = new StringWriter();
        marshaller.marshal(myClass, stringWriter);
        System.out.println(stringWriter.toString());

    }
}
