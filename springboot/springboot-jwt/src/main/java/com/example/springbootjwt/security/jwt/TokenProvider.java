package com.example.springbootjwt.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private Key key;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    @PostConstruct
    public void init() {
        byte[] keyBytes;
        String secret = "XX#$%()(#*!()!KL<><MQLMNQNQJQK sdfkjsdrow32234545fdf>?N<:{LWPW";
        if (!StringUtils.isEmpty(secret)) {
            log.warn("Warning: the JWT key used is not Base64-encoded. " +
                    "We recommend using the `jhipster.security.authentication.jwt.base64-secret` key for optimum security.");
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        } else {
            log.debug("Using a Base64-encoded JWT secret key");
            keyBytes = Decoders.BASE64.decode("f3973b64918e4324ad85acea1b6cbec5");
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
        //50秒过期啊兄弟，加油
        this.tokenValidityInMilliseconds = 1000 * 50;
    }

    /**
     * 創建token
     *
     * @return
     */
    public String createToken() {
        // 签名作者
        String authorities = "zhangsan, lisi";
        long now = (new Date()).getTime();

        //過期時間
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject("user")
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(validity)
                .compact();
//        Map<String,Object> map=new HashMap<>();
//        map.put("uid",user.getId());
//        map.put("exp",DateTime.now().plusSeconds(40).toDate().getTime()/1000);
//
    }

    /**
     * @data: 2022/7/4-下午2:41
     * @User: zhaozhiwei
     * @method: generatorToken
      * @param payLoad :
     * @return: java.lang.String
     * @Description: 生成的token可以带有用户信息, 一般适用放到cookie中只用,放header里内容会比较大
     */
    public String generatorToken(Map<String, Object> payLoad) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            return Jwts.builder()
                    .setPayload(objectMapper.writeValueAsString(payLoad))
                    .signWith(generatorKey(), SignatureAlgorithm.HS256)
                    .compact();

        } catch (JsonProcessingException e) {

            e.printStackTrace();
        }
        return null;
    }

    public Claims phaseToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().
                toString().replace("-",""));
    }

    /**
     * 生成key
     * @return
     */
    private Key generatorKey(){
        SignatureAlgorithm saa=SignatureAlgorithm.HS256;
        byte[] bin= DatatypeConverter.parseBase64Binary
                ("f3973b64918e4324ad85acea1b6cbec5f3973b64918e4324ad85acea1b6cbec5");
        Key key=new SecretKeySpec(bin,saa.getJcaName());
        return key;
    }
}
