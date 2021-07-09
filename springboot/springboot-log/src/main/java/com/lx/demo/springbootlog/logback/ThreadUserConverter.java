package com.lx.demo.springbootlog.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: ThreadUserConverter
 * @Package com/lx/demo/springbootlog/logback/ThreadUserConverter.java
 * @Description:
 * 自定义输出信息, 如果可以获取全局信息可以写入
 * 可以通过thread local获取线程下信息
 * @date 2021/6/22 上午11:42
 */
public class ThreadUserConverter extends ClassicConverter {

    public String convert(ILoggingEvent le) {
        StringBuffer sb = new StringBuffer();
        //先取SecureUtil中的信息
//		if (SecureUtil.getCurrentUser() != null) {
//			sb.append("thread userinfo：name=");
//			sb.append(SecureUtil.getCurrentUser().getCode());
//			sb.append(",userid=");
//			sb.append(SecureUtil.getCurrentUser().getGuid());
//			sb.append(",province=");
//			sb.append(SecureUtil.getUserSelectProvince());
//			sb.append(",year=");
//			sb.append(SecureUtil.getUserSelectYear());
//		} else {
//			sb.append("current thread had no userinfo");
//		}
        sb.append("custom thread userinfo: ");
        return sb.toString();
    }

}
