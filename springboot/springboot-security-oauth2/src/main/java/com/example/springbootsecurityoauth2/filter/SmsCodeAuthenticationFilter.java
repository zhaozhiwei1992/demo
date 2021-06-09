package com.example.springbootsecurityoauth2.filter;

import com.example.springbootsecurityoauth2.domain.SmsCodeAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public SmsCodeAuthenticationFilter(String loginProcessUrlMobile) {
        super(new AntPathRequestMatcher(loginProcessUrlMobile, "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);

        // 封装令牌
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        // 开始认证
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainMobile(HttpServletRequest request) {
        String mobile = request.getParameter("mobile");
        if (mobile == null) {
            mobile = "";
        }
        return mobile.trim();
    }

    /**
     * @data: 2021/6/9-上午9:23
     * @User: zhaozhiwei
     * @method: setDetails
      * @param request :
 * @param authRequest :
     * @return: void
     * @Description: 补充验证码相关参数到明细中，以便后续校验
     */
    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {

        if (authRequest != null) {
            Map detailsExtension = new HashMap();
            final Object o = authenticationDetailsSource.buildDetails(request);
            WebAuthenticationDetails webAuthenticationDetails = null;
            if(o instanceof WebAuthenticationDetails){
               webAuthenticationDetails = (WebAuthenticationDetails) o;
            }
            if (webAuthenticationDetails.getRemoteAddress() != null) {
                detailsExtension.put("remoteAddress", webAuthenticationDetails.getRemoteAddress());
            }
            if (webAuthenticationDetails.getSessionId() != null) {
                detailsExtension.put("sessionId", webAuthenticationDetails.getSessionId());
            }
            detailsExtension.putAll(getParameterMap(request));
            authRequest.setDetails(detailsExtension);
        }
    }

    /**
     * @data: 2021/6/9-上午9:35
     * @User: zhaozhiwei
     * @method: getParameterMap
      * @param request :
     * @return: java.util.Map
     * @Description: request信息转map
     */
    public Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
}