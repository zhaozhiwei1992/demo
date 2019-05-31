package com.lx.demo.web.rest;

import com.lx.demo.domain.User;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.ArrayList;

/**
 * org.glassfish.jersey.message.internal.WriterInterceptorExecutor$TerminalWriterInterceptor aroundWriteTo
 * 如果报错，需要检查实体类是否加入了注解 XmlRootElement
 */
@Path("/users")
public class UserResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    /**
     * curl -H "Accept:application/xml" -X GET http://127.0.0.1:8080/rest/users
     * @return
     */
    // This method is called if XML is requested
    @GET
    @Produces({MediaType.APPLICATION_XML})
    public User getXML() {
        User User = new User();
        User.setId(0L);
        User.setName("zhangsan");
        User.setAge(0);
        return User;
    }

    // This method is called if JSON is requested
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public User getJSON() {
        User User = new User();
        User.setId(0L);
        User.setName("lisi");
        User.setAge(0);

        return User;
    }

    /**
     * curl -H "Accept:text/xml" -X GET http://127.0.0.1:8080/rest/users
     * @return
     */
    // This can be used to test the integration with the browser
    @GET
    @Produces({ MediaType.TEXT_XML })
    public User getHTML() {
        User User = new User();
        User.setId(0L);
        User.setName("wangwu");
        User.setAge(0);

        return User;
    }

    /**
     * url中传入参数
     * 请求: curl -H "Accept:text/xml" -X GET http://127.0.0.1:8080/rest/users/zhangsan
     * @param name
     * @return
     */
    @GET
    @Path("{name}")
    @Produces({ MediaType.TEXT_XML })
    public User save(@PathParam("name")String name){
        User user = new User();
        user.setId(0L);
        user.setName(name);
        user.setAge(0);
        return user;
    }

    /**
     * 模拟form表单post请求
     * @param id
     * @param name
     * @param age
     * @param servletResponse
     * @return
     * @throws IOException
     */
    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public User newTodo(@FormParam("id") String id,
                        @FormParam("name") String name,
                        @FormParam("age") String age,
                        @Context HttpServletResponse servletResponse) throws IOException {
        ArrayList<User> users = new ArrayList<User>();
        User user = new User();
        user.setId(Long.parseLong(id));
        user.setName(name);
        user.setAge(Integer.parseInt(age));
        users.add(user);
        return user;
    }
}
