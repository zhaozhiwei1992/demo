package com.lx.demo.springbootlog.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.lx.demo.springbootlog.service.TraceInfo;

import java.util.Map;
import java.util.Objects;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: ThreadUserConverter
 * @Package com/lx/demo/springbootlog/logback/ThreadUserConverter.java
 * @Description:
 * 自定义输出信息, 如果可以获取全局信息可以写入
 * 可以通过thread local获取线程下信息, 作为traceinfo 日志输出
 *
 * @date 2021/6/22 上午11:42
 */
public class ThreadUserConverter extends ClassicConverter {

    public String convert(ILoggingEvent le) {
        StringBuffer sb = new StringBuffer();
        sb.append(" custom thread traceinfo: ");
        final Map<String, Object> traceInfo = TraceInfo.getTraceInfo();
        if(!Objects.isNull(traceInfo)){
            sb.append("threadid=").append(traceInfo.get("threadid")).append(", ")
                    .append("requestid=").append(traceInfo.get("requestid"));
        }
        return sb.toString();
    }

}
