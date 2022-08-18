package com.example.springbootbucket4j.aop;

import io.github.bucket4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

/**
 * @Title: Bucket4jFilter
 * @Package com/example/springbootbucket4j/aop/Bucket4jFilter.java
 * @Description:
 * 接口过滤测试, 防止干爆
 * @author zhaozhiwei
 * @date 2022/8/18 上午10:33
 * @version V1.0
 */
public class Bucket4jFilter extends OncePerRequestFilter {

    /**
     * @data: 2022/8/18-下午2:27
     * @User: zhaozhiwei
     * @method:
      * @param null :
     * @return:
     * @Description: 描述
    // 最大桶容量20, 每次最多20个请求能通过
     */
    private static final long OVERDRAFT = 20;

    /**
     * @data: 2022/8/18-下午2:28
     * @User: zhaozhiwei
     * @method:
      * @param null :
     * @return:
     * @Description:
    // 每个周期生成的令牌个数, 跟SECONDS_PER_PERIOD配套, 如果没有令牌，光桶也没用
     */
    private static final long MAX_REQUESTS_PER_PERIOD = 10;

    /**
     * @data: 2022/8/18-下午2:28
     * @User: zhaozhiwei
     * @method:
      * @param null :
     * @return:
     * @Description:
    // 每个周期的长度, 刷新令牌的时间, 1秒
     */
    private static final long SECONDS_PER_PERIOD = 1;

    public Bucket4jFilter(CacheManager simpleCacheManager) {
        this.simpleCacheManager = simpleCacheManager;
    }

    private Bucket createNewBucket() {
        Refill refill = Refill.greedy(MAX_REQUESTS_PER_PERIOD, Duration.ofSeconds(SECONDS_PER_PERIOD));
        Bandwidth limit = Bandwidth.classic(OVERDRAFT, refill);
        return Bucket.builder().addLimit(limit).build();
    }

    private CacheManager simpleCacheManager;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    )
            throws ServletException, IOException {
        final Cache cacheBucket = simpleCacheManager.getCache("cache-bucket");
        // 获取这次请求的拦截标识，例如ip或username
        // 每个用户一个桶
        String id = request.getParameter("username");

        // 获取标识符对应的令牌桶
        Bucket bucket = cacheBucket.get(id, Bucket.class);
        if (bucket == null) {
            bucket = createNewBucket();
            cacheBucket.put(id, bucket);
        }
        // 尝试从桶中获取token
        if (bucket.tryConsume(1)) {
            // 如果获取成功则放行
            chain.doFilter(request, response);
        } else {
            // 如果不成功则返回429
            response.setContentType("test/plain");
            response.setStatus(429);
            response.getWriter().append("Too many requests. \n");
        }
    }
}