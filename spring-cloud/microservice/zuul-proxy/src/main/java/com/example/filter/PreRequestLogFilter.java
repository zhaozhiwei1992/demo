package com.example.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Slf4j
public class PreRequestLogFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        // 在PRE_DECORATION_FILTER_ORDER之前执行
        return FilterConstants.PRE_DECORATION_FILTER_ORDER -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 测试:
     * http://127.0.0.1:10086/user-service-provider/users
     *
     * 多次请求测试, 通过客户端访问服务, 测试链路追踪
     * seq 5 |xargs -i echo "http://127.0.0.1:10086/user-service-client/users" |xargs -n 1 curl -X GET
     * 反馈:
     * 2019-08-03 14:13:40.425  INFO 24334 --- [io-10086-exec-4] com.example.filter.PreRequestLogFilter
     *  : send GET request to http://127.0.0.1:10086/user-service-provider/users
     *  com.example.filter.PreRequestLogFilter   : send GET request to http://127.0.0.1:10086/user-service-client/users
     * c.n.zuul.http.HttpServletRequestWrapper  : Path = null
     * c.n.zuul.http.HttpServletRequestWrapper  : Transfer-Encoding = null
     * c.n.zuul.http.HttpServletRequestWrapper  : Content-Encoding = null
     * c.n.zuul.http.HttpServletRequestWrapper  : Content-Length header = -1
     * o.s.c.n.z.f.r.s.AbstractRibbonCommand    : The Hystrix timeout of 60000ms for the command user-service-client
     * is set lower than the combination of the Ribbon read and connect timeout, 180000ms.
     * c.n.loadbalancer.ZoneAwareLoadBalancer   : Zone aware logic disabled or there is only one zone
     * c.n.loadbalancer.LoadBalancerContext     : user-service-client using LB returned Server: localhost:8080 for
     * request /users
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        final RequestContext currentContext = RequestContext.getCurrentContext();
        final HttpServletRequest request = currentContext.getRequest();
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL());
        return null;
    }
}
