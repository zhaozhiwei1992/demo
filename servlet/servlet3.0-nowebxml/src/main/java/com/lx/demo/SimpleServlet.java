package com.lx.demo;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * servlet的注解支持
 *等价的 web.xml 配置形式如下：
 *
 * <servlet>
 *     <display-name>ss</display-name>
 *     <servlet-name>SimpleServlet</servlet-name>
 *     <servlet-class>footmark.servlet.SimpleServlet</servlet-class>
 *     <load-on-startup>-1</load-on-startup>
 *     <async-supported>true</async-supported>
 *     <init-param>
 *         <param-name>username</param-name>
 *         <param-value>tom</param-value>
 *     </init-param>
 * </servlet>
 * <servlet-mapping>
 *     <servlet-name>SimpleServlet</servlet-name>
 *     <url-pattern>/simple</url-pattern>
 * </servlet-mapping>
 *
 * name	String	指定 Servlet 的 name 属性，等价于 <servlet-name>。如果没有显式指定，则该 Servlet 的取值即为类的全限定名。
 * value	String[]	该属性等价于 urlPatterns 属性。两个属性不能同时使用。
 * urlPatterns	String[]	指定一组 Servlet 的 URL 匹配模式。等价于 <url-pattern> 标签。
 * loadOnStartup	int	指定 Servlet 的加载顺序，等价于 <load-on-startup> 标签。
 * initParams	WebInitParam[]	指定一组 Servlet 初始化参数，等价于 <init-param> 标签。
 * asyncSupported	boolean	声明 Servlet 是否支持异步操作模式，等价于 <async-supported> 标签。
 * description	String	该 Servlet 的描述信息，等价于 <description> 标签。
 * displayName	String	该 Servlet 的显示名，通常配合工具使用，等价于 <display-name> 标签。
 */
@WebServlet(urlPatterns = {"/simple"}, asyncSupported = true,
        loadOnStartup = -1, name = "SimpleServlet", displayName = "ss",
        initParams = {@WebInitParam(name = "username", value = "tom")}
)
public class SimpleServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("获取初始化参数: username=" + config.getInitParameter("username"));
    }

    /**
     * 必须重写get方法并反馈，不然报错
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }
}

