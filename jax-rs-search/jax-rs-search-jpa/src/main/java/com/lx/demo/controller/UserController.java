package com.lx.demo.controller;

import com.lx.demo.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://stackoverflow.com/questions/42087885/how-do-i-filter-data-in-a-restful-way-using-spring
 * https://github.com/jirutka/rsql-parser
 * https://www.jdon.com/dl/best/rest-api-search-language-rsql-fiql.html
 */
@RestController
@RequestMapping(value = "/users")
public class UserController {

    @GetMapping
    public List<User> search(@RequestParam(value = "search") String search) {
//        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
//        Pattern pattern = Pattern.compile("(\w+?)(:|<|>)(\w+?),");
//        Matcher matcher = pattern.matcher(search + ",");
//        while (matcher.find()) {
//            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
//        }
//
//        Specification<User> spec = builder.build();
        return null;
    }
}
