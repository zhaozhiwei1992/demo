package com.lx.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.lx.demo
 * @Description: TODO
 * @date 2021/5/25 上午10:13
 */
@WebServlet("/echo")
public class EchoServlet extends HttpServlet {

    /**
     * @data: 2021/5/25-上午10:23
     * @User: zhaozhiwei
     * @method: doGet
      * @param req :
 * @param resp :
     * @return: void
     * @Description: 描述
     * HTTP/1.1 200 OK
     * Date: Tue, 25 May 2021 02:22:31 GMT
     * Content-Type: text/html;charset=utf-8
     * Content-Length: 13
     * Server: Jetty(9.3.0.M1)
     *
     * jetty插件: http://127.0.0.1:8080/jetty/echo
     * tomcat war only:  http://127.0.0.1:8080/tomcat/echo
     * tomcat run: Running war on http://localhost:8080/servlet
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 设置后netty不在乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        // 这个必须有
        resp.setContentType("text/html;charset=utf-8");

       // 输出一段信息
        resp.getWriter().println("请求成功");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
