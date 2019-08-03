package com.lx.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.ODataConsumers;
import org.odata4j.core.OEntity;
import org.odata4j.expression.CommonExpression;
import org.odata4j.expression.ExpressionParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Slf4j
public class SearchController {

    @GetMapping(value = "/find")
    public List findAllByRsql(@RequestParam(value = "search") String search) {
        // create consumer instance
        search="http://localhost:8080/users/find?search=contains(CompanyName,%27Alfreds%27)";
        CommonExpression actual = ExpressionParser.parse(search);
        System.out.println(actual);
//        assertSame(expected, actual);

//        String serviceUrl = "http://services.odata.org/OData/OData.svc/";
//        ODataConsumer consumer = ODataConsumers.create(serviceUrl);

// list category names
//        for (OEntity category : consumer.getEntities("Categories").execute()) {
//            String categoryName = category.getProperty("Name", String.class).getValue();
//            System.out.println("Category name: " + categoryName);
//        }

        return null;
    }

}

