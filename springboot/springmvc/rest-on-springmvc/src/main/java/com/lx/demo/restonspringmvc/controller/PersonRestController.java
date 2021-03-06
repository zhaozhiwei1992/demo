package com.lx.demo.restonspringmvc.controller;

import com.lx.demo.restonspringmvc.model.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@RestController
public class PersonRestController {

    /**
     * id中写入的参数会直接传到变量中
     *
     * @param id
     * @param name
     * @return
     */
    @GetMapping("/person/login/{id}")
    public Person login(@PathVariable long id, @RequestParam(required = false) String name){
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        return person;
    }

    @PostMapping(value = "/person/json/to/properties",
            consumes = "text/plain",
            produces = "application/properties+person" // 响应类型
    )
    public Person personJsonToProperties(@RequestParam String json) {

        // JSON to Map
        // Map to Properties
        return null;
    }

    @PostMapping(value = "/person/json/to/properties",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = "application/properties+person" // 响应类型
    )
    public Person personJsonToProperties(@RequestBody Person person) {
        // @RequestBody 的内容是 JSON
        // 响应的内容是 Properties
        return person;
    }

    @PostMapping(value = "/person/properties/to/json",
            consumes = "application/properties+person", // 请求类型 //  Accept
            produces =  MediaType.APPLICATION_JSON_UTF8_VALUE// 响应类型 // Content-Type
    )
    public Person personPropertiesToJson(@RequestBody Person person) {
        // @RequestBody 的内容是 Properties
        // 响应的内容是 JSON
        return person;
    }

    @PostMapping("/bdg-server/oadata")
    public List<Person> oa(
            @Pattern(regexp = "^[0-9]{4,6}", message = "请传入正确区划") @RequestParam("admDivCode") String admDivCode,
            @Pattern(regexp = "^\\d{4}$", message = "请传入正确年度") @RequestParam("year") String year,
            @Valid @RequestBody List<Person> personList){
        System.out.println(personList);
        return personList;
    }
}
