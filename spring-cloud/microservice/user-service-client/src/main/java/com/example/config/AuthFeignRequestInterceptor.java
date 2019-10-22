package com.example.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.server.session.HeaderWebSessionIdResolver;

import java.util.Base64;

/**
 * 每一次feign请求增加认证信息
 */
public class AuthFeignRequestInterceptor  implements RequestInterceptor {

    private final static Log logger = LogFactory.getLog(AuthFeignRequestInterceptor.class);

    public static final String BEARER = "Bearer";

    public static final String AUTHORIZATION = "Authorization";

    /**
     * Called for every request. Add data using methods on the supplied {@link RequestTemplate}.
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        String token = getToken();
        if(StringUtils.isNotBlank(token)) {
            String accessToken = String.format("%s %s", BEARER, getToken());
            template.header(AUTHORIZATION, accessToken);
            logger.info("设置feign调用token信息"+accessToken);
        }else{
            logger.debug("远程feign调用没有TOKEN。。");
        }
        try {
            String sessionId = null;
            if(RequestContextHolder.getRequestAttributes()!=null) {
                sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

            }
            if (null == sessionId) {
                sessionId = getSessionId();
            }
            if (null != sessionId) {
                template.header("Cookie", HeaderWebSessionIdResolver.DEFAULT_HEADER_NAME + "=" + base64Encode(sessionId));
            }
        } catch (Exception e) {
            logger.warn("MyRequestInterceptor exception: "+e.getMessage());
        }
    }

    /**
     * Encode the value using Base64.
     * @param value the String to Base64 encode
     * @return the Base64 encoded value
     * @since 1.2.2
     */
    private String base64Encode(String value) {
        byte[] encodedCookieBytes = Base64.getEncoder().encode(value.getBytes());
        return new String(encodedCookieBytes);
    }
    private String getToken(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth instanceof UsernamePasswordAuthenticationToken) {
//            Object details = auth.getDetails();
//            if (details instanceof Map) {
//                Map oauth = (Map) details;
//                return (String)oauth.get("token");
//            }
//        }
        return null;
    }

    private String getSessionId(){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth instanceof UsernamePasswordAuthenticationToken) {
//            Object details = auth.getDetails();
//            if (details instanceof Map) {
//                Map oauth = (Map) details;
//                return (String)oauth.get("sessionId");
//            }
//        }
        return null;
    }
}
