package com.lx.demo.controller;

import com.lx.demo.domain.User;
import org.apache.cxf.jaxrs.ext.search.SearchCondition;
import org.apache.cxf.jaxrs.ext.search.SearchContext;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.List;
import java.util.Map;

/**
 * https://stackoverflow.com/questions/42087885/how-do-i-filter-data-in-a-restful-way-using-spring
 * https://github.com/jirutka/rsql-parser
 * https://www.jdon.com/dl/best/rest-api-search-language-rsql-fiql.html
 */
@Path(value = "/users")
public class UserController {

    private Map<Long, User> users;

    /**
     * 没有卵用，　懒得测试了
     */
    @Context
    private SearchContext searchContext;

    @GET
    @Path("/search")
    public List<User> search(@RequestParam(value = "search") String search) {
        SearchCondition<User> sc = searchContext.getCondition(User.class);
        // SearchCondition#isMet method can also be used to build a list of matching beans

        // iterate over all the values in the books map and return a collection of matching beans
        List<User> found = sc.findAll(users.values());
        return null;
    }
}
