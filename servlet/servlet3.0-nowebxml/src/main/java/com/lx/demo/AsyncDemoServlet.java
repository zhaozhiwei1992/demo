package com.lx.demo;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * servlet3.0异步测试
 * {@link https://www.ibm.com/developerworks/cn/java/j-lo-servlet30/index.html}
 */
@WebServlet(urlPatterns = "/async-demo", asyncSupported = true)
public class AsyncDemoServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("进入Servlet的时间：" + new Date() + ".\n");
        out.flush();

        //在子线程中执行业务调用，并由其负责输出响应，主线程退出
        AsyncContext ctx = req.startAsync();

        //设置超时时间后会进入timeout方法
//        ctx.setTimeout(1000);

        // 3.0提供了异步事件处理相关问题
        ctx.addListener(new AsyncListener() {
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                // 做一些清理工作或者其他
                System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
            }

            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
            }

            public void onError(AsyncEvent asyncEvent) throws IOException {
                System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
            }

            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
                System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());
            }
        });
        new Thread(new Executor(ctx)).start();

        out.println("结束Servlet的时间：" + new Date() + ".\n");
        out.flush();
    }

    class Executor implements Runnable {
        private AsyncContext ctx = null;
        public Executor(AsyncContext ctx){
            this.ctx = ctx;
        }

        public void run(){
            try {
                //等待十秒钟，以模拟业务方法的执行
                Thread.sleep(10000);
                PrintWriter out = ctx.getResponse().getWriter();
                out.println("业务处理完毕的时间：" + new Date() + ".\n");
                out.flush();
                ctx.complete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


