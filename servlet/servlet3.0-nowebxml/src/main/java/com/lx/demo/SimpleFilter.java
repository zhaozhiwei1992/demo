package com.lx.demo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * filterName	String	指定过滤器的 name 属性，等价于 <filter-name>
 * value	String[]	该属性等价于 urlPatterns 属性。但是两者不应该同时使用。
 * urlPatterns	String[]	指定一组过滤器的 URL 匹配模式。等价于 <url-pattern> 标签。
 * servletNames	String[]	指定过滤器将应用于哪些 Servlet。取值是 @WebServlet 中的 name 属性的取值，或者是 web.xml 中 <servlet-name> 的取值。
 * dispatcherTypes	DispatcherType	指定过滤器的转发模式。具体取值包括：
 * ASYNC、ERROR、FORWARD、INCLUDE、REQUEST。
 * initParams	WebInitParam[]	指定一组过滤器初始化参数，等价于 <init-param> 标签。
 * asyncSupported	boolean	声明过滤器是否支持异步操作模式，等价于 <async-supported> 标签。
 * description	String	该过滤器的描述信息，等价于 <description> 标签。
 * displayName	String	该过滤器的显示名，通常配合工具使用，等价于 <display-name> 标签。
 */
@WebFilter(servletNames = {"SimpleServlet"},filterName="SimpleFilter", urlPatterns = "/simple")
public class SimpleFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    }

    /**
     * todo 这里为毛跑了俩次  http://127.0.0.1:8080/simple
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("访问" + servletRequest.getRemoteHost());
        filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
    }
}

