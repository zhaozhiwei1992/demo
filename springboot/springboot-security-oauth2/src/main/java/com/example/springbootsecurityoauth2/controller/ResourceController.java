package com.example.springbootsecurityoauth2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * rest接口同样登录以后才能访问
 */
@RestController
@RequestMapping(value = "/rest")
public class ResourceController {
    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

    /**
     * @data: 2021/6/8-下午4:24
     * @User: zhaozhiwei
     * @method: hello
      * @param authentication :
     * @return: java.lang.String
     * @Description:
     * curl -X GET -H 'Authorization: bearer 49ab5f2d-ca0e-4eae-9527-6d625179d4ba' -i http://localhost:8080/rest/hello
     */
    @GetMapping("/hello")
    public String hello(Authentication authentication){
        log.info("resource: user {}", authentication);
        return "hello:"+ authentication.getName();
    }

    @GetMapping("/sayhello")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String sayhello(Authentication authentication){
        log.info("resource: user {} has role ROLE_USER", authentication);
        return "sayhello:"+ authentication.getName();
    }

    /**
     * @EnableGlobalMethodSecurity(prePostEnabled = true) 需要打开
     * There was an unexpected error (type=Forbidden, status=403).
     * Forbidden
     * @param authentication
     * @return
     */
    @GetMapping("/sayhello1")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String sayhello1(Authentication authentication){
        log.info("resource: user {} has role ROLE_ADMIN", authentication);
        return "sayhello:"+ authentication.getName();
    }

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * @data: 2021/6/9-下午8:12
     * @User: zhaozhiwei
     * @method: me
      * @param auth :
 * @param request :
     * @return: java.lang.String
     * @Description:
     * {
     * 	"authorities": [{
     * 		"authority": "ROLE_ADMIN"
     *        }, {
     * 		"authority": "ROLE_USER"
     *    }],
     * 	"details": {
     * 		"remoteAddress": "127.0.0.1",
     * 		"sessionId": null,
     * 		"tokenValue": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCIsInJlYWQiLCJ3cml0ZSJdLCJmb28iOiJiYXIiLCJleHAiOjE2MjMyNDczMjUsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwianRpIjoiOGU1Yzc3MjItZjEwMi00OWZiLTkxZmMtMGRlMjIxNmU0MzZmIiwiY2xpZW50X2lkIjoiY2xpZW50SWQifQ.W9bhA27BivLJglu9wCgisVOAYhqVaKlXwH_zItmyC7Y",
     * 		"tokenType": "bearer",
     * 		"decodedDetails": null
     *    },
     * 	"authenticated": true,
     * 	"userAuthentication": {
     * 		"authorities": [{
     * 			"authority": "ROLE_ADMIN"
     *        }, {
     * 			"authority": "ROLE_USER"
     *        }],
     * 		"details": null,
     * 		"authenticated": true,
     * 		"principal": "admin",
     * 		"credentials": "N/A",
     * 		"name": "admin"
     *    },
     * 	"oauth2Request": {
     * 		"clientId": "clientId",
     * 		"scope": ["all", "read", "write"],
     * 		"requestParameters": {
     * 			"client_id": "clientId"
     *        },
     * 		"resourceIds": [],
     * 		"authorities": [],
     * 		"approved": true,
     * 		"refresh": false,
     * 		"redirectUri": null,
     * 		"responseTypes": [],
     * 		"extensions": {},
     * 		"grantType": null,
     * 		"refreshTokenRequest": null
     *    },
     * 	"credentials": "",
     * 	"principal": "admin",
     * 	"clientOnly": false,
     * 	"name": "admin"
     * }
     */
    @GetMapping(value = "/echo/jwt", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String echo(Authentication auth, HttpServletRequest request) throws JsonProcessingException, UnsupportedEncodingException {
        String header = request.getHeader("Authorization");
        String token = header.split("bearer ")[1];
        Claims claims = Jwts.parser().setSigningKey("zzw_JwtKey".getBytes("UTF-8")).parseClaimsJws(token).getBody();
        String foo = (String) claims.get("foo");

        System.err.println(foo);

        return objectMapper.writeValueAsString(auth);
    }

}

