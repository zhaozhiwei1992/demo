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
     * 反馈:
     * 2019-08-03 14:13:40.425  INFO 24334 --- [io-10086-exec-4] com.example.filter.PreRequestLogFilter
     *  : send GET request to http://127.0.0.1:10086/user-service-provider/users
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
