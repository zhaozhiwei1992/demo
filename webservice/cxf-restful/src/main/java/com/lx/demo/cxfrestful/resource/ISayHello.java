package com.lx.demo.cxfrestful.resource;

import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * curl -X GET http://127.0.0.1:8080/services/ws/sayHello/zhangsan
 */
@Path("/sayHello")
@Service
public interface ISayHello {

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    String sayHello(@PathParam("name") String name);
}
