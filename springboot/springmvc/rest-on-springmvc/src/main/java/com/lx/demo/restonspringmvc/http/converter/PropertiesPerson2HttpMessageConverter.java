package com.lx.demo.restonspringmvc.http.converter;

import com.lx.demo.restonspringmvc.model.Person;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * 自定义扩展描述
 *https://docs.spring.io/spring-boot/docs/2.0.8.RELEASE/reference/htmlsingle/#boot-features-spring-mvc-message-converters
 * Spring MVC uses the HttpMessageConverter interface to convert HTTP requests and responses.
 * Sensible defaults are included out of the box.
 * For example, objects can be automatically converted to JSON (by using the Jackson library) or
 * XML (by using the Jackson XML extension,
 * if available, or by using JAXB if the Jackson XML extension is not available). By default, strings are encoded in UTF-8.
 */
public class PropertiesPerson2HttpMessageConverter extends AbstractHttpMessageConverter<Person> {
    public PropertiesPerson2HttpMessageConverter(){
        super(MediaType.valueOf("application/properties+person"));
        setDefaultCharset(Charset.forName("UTF-8"));
    }

    /**
     * 指定响应 produces时候匹配， 走该方法
     * @PostMapping(value = "/person/json/to/properties",
     *             consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
     *             produces = "application/properties+person" // 响应类型
     *     )
     * @param person
     * @param httpOutputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Person person, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream outputStream = httpOutputMessage.getBody();
        Properties properties = new Properties();
        properties.setProperty("person.id",String.valueOf(person.getId()));
        properties.setProperty("person.name",person.getName());
        properties.store(new OutputStreamWriter(outputStream,getDefaultCharset()),"Written by web server");
    }

    /**
     * 只支持转换person
     * @param aClass
     * @return
     */
    @Override
    protected boolean supports(Class aClass) {
        return aClass.isAssignableFrom(Person.class);
    }

    /**
     * 请求内容转换为person对象, 请求类型匹配走该方法
     *  @PostMapping(value = "/person/properties/to/json",
     *             consumes = "application/properties+person", // 请求类型 // Content-Type
     *             produces =  MediaType.APPLICATION_JSON_UTF8_VALUE// 响应类型 // Accept
     *     )
     * @param aClass
     * @param httpInputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    protected Person readInternal(Class aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        /**
         * person.id = 1
         * person.name = 小马哥
         */
        InputStream inputStream = httpInputMessage.getBody();

        Properties properties = new Properties();
        // 将请求中的内容转化成Properties
        properties.load(new InputStreamReader(inputStream,getDefaultCharset()));
        // 将properties 内容转化到 Person 对象字段中
        Person person = new Person();
        person.setId(Long.valueOf(properties.getProperty("person.id")));
        person.setName(properties.getProperty("person.name"));
        return person;
    }

}
