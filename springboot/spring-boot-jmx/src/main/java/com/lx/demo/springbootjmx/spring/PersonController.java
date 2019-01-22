package com.lx.demo.springbootjmx.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通过spring来管理jmx
 * 参考
 * https://docs.spring.io/spring/docs/5.0.10.RELEASE/spring-framework-reference/integration.html#jmx
 * https://docs.spring.io/spring-boot/docs/2.0.6.RELEASE/reference/htmlsingle/#production-ready-jolokia
 * https://jolokia.org/reference/html/protocol.html#get-request (http://localhost:8080/jolokia/read/java.lang:type=Memory/HeapMemoryUsage)
 */
@RestController
public class PersonController {

    @Autowired
    Person person;

    /**
     * 参数列表中的int类型是必须赋值的，不然。。
     * java.lang.IllegalStateException: Optional int parameter 'age' is present but cannot be translated into a null value due to being declared as a primitive type.
     * @param name
     * @return
     */
    @GetMapping("/person")
    public Person getPerson(@RequestParam(required = false) String name){
        person.setId(1);
        if(name != null){
            person.setName(name);
        }
        return person;
    }
}
