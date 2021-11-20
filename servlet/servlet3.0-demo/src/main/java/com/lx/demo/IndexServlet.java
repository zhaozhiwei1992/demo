package com.lx.demo;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet报错：405 HTTP method GET is not supported by this URL问题解决方法
 * 问题产生原因：
 *
 * 1，继承自HttpServlet的Servlet没有重写对于请求和响应的处理方法：doGet或doPost等方法；默认调
 *
 * 用父类的doGet或doPost等方法；
 *
 * 2，父类HttpServlet的doGet或doPost等方法覆盖了你重写的doGet或doPost等方法；
 *
 * 不管是1或2，父类HttpServlet的doGet或doPost等方法的默认实现是返回状态代码为405的HTTP错误表示
 *
 * 对于指定资源的请求方法不被允许。
 *
 * 问题解决方法：
 *
 * 1，子类重写doGet或doPost等方法；
 *
 * 2，在你扩展的Servlert中重写doGet或doPost等方法来处理请求和响应时 不要调用父类HttpServlet的
 *
 * doGet或doPost等方法，即去掉super.doGet(request, response)和super.doPost(request, response);
 *
 *
 * web3.0 与在web.xml中加入servletmapping效果一样
 * tomcat8 maven插件启动
 * 请求地址, http://127.0.0.1:8080/servlet/indexServlet
 */
//@WebServlet("/indexServlet")
public class IndexServlet extends HttpServlet {
    public IndexServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final ServletConfig servletConfig = getServletConfig();
        response.getWriter()
                .append("Served at: ")
                .append(request.getContextPath())
                .append("servlet config \r\n")
                .append(servletConfig.getServletName());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
