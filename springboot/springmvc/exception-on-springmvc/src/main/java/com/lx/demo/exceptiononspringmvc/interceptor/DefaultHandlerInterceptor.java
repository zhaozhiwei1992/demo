package com.lx.demo.exceptiononspringmvc.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 自定义拦截器
 */
@Slf4j
public class DefaultHandlerInterceptor implements HandlerInterceptor {

    /**
     * 拦截器：HandlerInterceptor 可以理解 Handler 到底是什么
     *
     * 			处理顺序：preHandle(true) -> Handler: HandlerMethod 执行(Method#invoke) -> postHandle -> afterCompletion
     * 					  preHandle(false)
     * @param request
     * @param response
     * @param handler  这里的handler不一定是handlermethod
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.printf("handler object : %s \n",handler.toString());
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
            for(MethodParameter methodParameter : methodParameters){
                log.info(methodParameter.getParameterName());
            }
            log.info("handler method : {} \n",handler.toString());
        }
        log.info("handler object : {} \n",handler.toString());
        return true;
    }

    /**
     * 取得参数值
     * @author SHANSHAN
     */
    public static String getValue(HttpServletRequest request){
        String value = "";
        Enumeration enu = request.getParameterNames();
        while(enu.hasMoreElements()){
            String paraName = (String)enu.nextElement();
            if (!"_dc".equals(paraName) && !"node".equals(paraName)){//_dc的参数不要
                String [] arr = request.getParameterValues(paraName);
                if (arr != null && arr.length > 1){
                    value += paraName+"="+ConvertObjectArrToStr(arr)+";";
                }else{
                    value += paraName+"="+request.getParameter(paraName)+";";
                }
            }
        }
        return value;
    }

    /**
     * 把对象列表,转化成逗号分隔的字符串
     * @author SHANSHAN
     */
    public static String ConvertObjectArrToStr(Object [] arr) {
        String result = "";
        if (arr != null && arr.length > 0) {
            for (int i = 0; i < arr.length; i++) {
                if (!"".equals(String.valueOf(arr[i]))) {
                    result += String.valueOf(arr[i]) + ",";
                }
            }
            if (!"".equals(result)) {
                result = result.substring(0, result.length() - 1);
            }
        }
        return result;
    }

}
