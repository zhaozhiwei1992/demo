package com.example.springbootjwt.web.rest;
import java.util.HashSet;
import java.time.Instant;
import com.example.springbootjwt.domain.Authority;

import com.example.springbootjwt.domain.User;
import com.example.springbootjwt.security.jwt.JWTFilter;
import com.example.springbootjwt.security.jwt.TokenProvider;
import com.example.springbootjwt.web.rest.vm.ResponseData;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.joda.time.DateTime;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    public UserJWTController(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * curl -X POST http://127.0.0.1:8080/api/authenticate -H "Content-Type:application/json;charset=utf8" -d '{"id":1,"username":"zhangsan","password":"1"}'
     * @return
     */
    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize() {
        String token = tokenProvider.createToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + token);
        return new ResponseEntity<>(new JWTToken(token), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/index")
    public String index(){
        return "请求成功";
    }

    @PostMapping("/login")
    public ResponseData doLogin(@PathParam("username") String username, @PathParam("password") String password,
                                HttpServletResponse response){
        ResponseData data=new ResponseData();

        Map<String,Object> map=new HashMap<>();
        User user = new User();
        user.setId(0L);
        user.setPassword(password);
        user.setFirstName(username);
        map.put("uid",user.getId());
        map.put("exp", DateTime.now().plusSeconds(40).toDate().getTime()/1000);

        String token = tokenProvider.generatorToken(map);
        response.addHeader("Set-Cookie",
                "access_token="+token+";Path=/;HttpOnly");
        return data;
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
