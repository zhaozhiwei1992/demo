package com.lx.demo.controller;

import com.lx.demo.domain.User;
import com.lx.demo.repository.CustompRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.*;
import lombok.extern.slf4j.Slf4j;
import org.odata4j.consumer.ODataConsumer;
import org.odata4j.consumer.ODataConsumers;
import org.odata4j.core.OEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * https://stackoverflow.com/questions/42087885/how-do-i-filter-data-in-a-restful-way-using-spring
 * https://github.com/jirutka/rsql-parser
 * https://www.jdon.com/dl/best/rest-api-search-language-rsql-fiql.html
 */
@RestController
@RequestMapping(value = "/users")
@Slf4j
public class UserController {

    @GetMapping(value = "/findByRsql")
    public List<User> findAllByRsql(@RequestParam(value = "search") String search) {
//        Node rootNode = new RSQLParser().parse(search);
//        final User accept = rootNode.accept(new NoArgRSQLVisitorAdapter<User>() {
//            public User visit(AndNode andNode) {
//                return null;
//            }
//
//            public User visit(OrNode orNode) {
//                return null;
//            }
//
//            public User visit(ComparisonNode comparisonNode) {
//                return null;
//            }
//        });
//        log.info("root node is: {}", rootNode);
//        log.info("accept is {}", accept);
//        Specification<User> spec = rootNode.accept(new CustompRsqlVisitor<User>());
//        log.info("spec : {}", spec);

//        How to use
//        Nodes are visitable, so to traverse the parsed AST (and convert it to SQL query maybe), you can implement the provided RSQLVisitor interface or simplified NoArgRSQLVisitorAdapter.
//        Node rootNode1 = new RSQLParser().parse("name==RSQL;version=ge=2.0");
//        log.info("rootnode1 is: {}", rootNode1);

        //How to add custom operators
        //Need more operators? The parser can be simply enhanced by custom FIQL-like comparison operators, so you can add your own.
//        Set<ComparisonOperator> operators = RSQLOperators.defaultOperators();
//        operators.add(new ComparisonOperator("=all=", true));
//        Node rootNode2 = new RSQLParser(operators).parse("genres=all=('thriller','sci-fi')");
//        log.info("rootnode2 is: {}", rootNode2);

        // create consumer instance
        String serviceUrl = "http://services.odata.org/OData/OData.svc/";
        ODataConsumer consumer = ODataConsumers.create(serviceUrl);

// list category names
        for (OEntity category : consumer.getEntities("Categories").execute()) {
            String categoryName = category.getProperty("Name", String.class).getValue();
            System.out.println("Category name: " + categoryName);
        }

        return null;
    }

}

