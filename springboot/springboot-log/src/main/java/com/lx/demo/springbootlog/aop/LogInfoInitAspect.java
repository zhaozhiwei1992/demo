package com.lx.demo.springbootlog.aop;

import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.springframework.web.context.request.RequestContextHolder.getRequestAttributes;

@Aspect
@Component
@Order(0) // 切面的顺序，越小越优先，对于多个切面Spring是使用责任链的模式 为了一开始将日志相关的参数初始化好，这里设置为最优先执行
public class LogInfoInitAspect {
    // 请求唯一ID
    private final String RequestId = "RequestId";
    // 请求的地址
    private final String RequestURI = "RequestURI";
    // 请求的线程ID
    private final String ThreadId = "ThreadId";
    // 请求的IP
    private final String IP = "IP";

    /**
     * @data: 2021/7/9-上午10:34
     * @User: zhaozhiwei
     * @method: initLogInfoController
      * @param joinPoint :
     * @return: java.lang.Object
     * @Description:
     * 描述
     * 2021-07-09 10:34:00.775 [127.0.0.1] [b4329657-233b-4695-a9ec-24b80596decf] [/] [http-nio-8080-exec-1] [22]
     * DEBUG c.l.d.s.web.rest.IndexResource - debug --> index
     *
    // 这里最好使用环绕通知，在执行完之后 将MDC中设置的值清空
    // 如果不使用环绕通知的话，可以使用Before设置值；使用After来清除值
    // 意思是将com.你的包路径.controller目录下以Controller结尾类的方法调用全部织入下面的代码块
     */
    @Around("within(com.lx.demo.springbootlog.web.rest..*Resource)")
    public Object initLogInfoController(ProceedingJoinPoint joinPoint) throws Throwable {
        // 请求对象
        HttpServletRequest request = ((ServletRequestAttributes) getRequestAttributes()).getRequest();
        // 响应对象
        HttpServletResponse response = ((ServletRequestAttributes) getRequestAttributes()).getResponse();

        // 获取客户端的IP
        String clientIP = getClientIP(request);
        if (StringUtils.isNotBlank(clientIP)) {
            MDC.put(IP, clientIP);
        }

        // 获取执行当前创作的 线程
        Thread thread = Thread.currentThread();
        // 设置线程ID
        MDC.put(ThreadId, String.valueOf(thread.getId()));

        // 获取请求地址
        String requestURI = request.getRequestURI();
        // 设置请求地址
        MDC.put(RequestURI, requestURI);

        // 生成当前请求的一个唯一UUID
        String requestId = UUID.randomUUID().toString();
        // 设置请求的唯一ID
        MDC.put(RequestId, requestId);
        // 将次唯一ID设置为响应头
        response.setHeader(RequestId, requestId);

        Object object = null;
        try {
            // 调用目标方法
            object = joinPoint.proceed();
            return object;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        } finally {
            // 注意，这里一定要清理掉
            // 否则可能会出现OOM的情况
            MDC.clear();
        }
    }

    /**
     * 在request中获取到客户端的IP
     *
     * @param request
     * @return
     */
    public String getClientIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}