package com.lx.demo.cxfrestful.resource;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 基于cxf webservice-restful
 */
@WebService(name="sayhello")
@Path("/hello")
public class SayHelloImpl implements ISayHello{

    @GET
    @Path("{name}")
    @Produces({MediaType.APPLICATION_JSON})
    public String sayHello(@PathParam("name") String name){
        String msg = "springboot cxf-restful --> hello" + name;
        System.out.println(msg);
        return  msg;
    }

    /**
     * curl -H "Accept:application/xml" -X GET http://127.0.0.1:8080/cxf/hello
     * @return
     */
    // This method is called if XML is requested
    @GET
    @Path("{name}")
    @Produces({MediaType.APPLICATION_XML})
    public String getXML(@PathParam("name") String name) {
        String msg = "springboot cxf-restful --> hello" + name;
        System.out.println(msg);
        return  msg;
    }
}
