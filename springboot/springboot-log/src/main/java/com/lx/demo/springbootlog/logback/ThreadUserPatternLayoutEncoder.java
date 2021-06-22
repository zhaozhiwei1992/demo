package com.lx.demo.springbootlog.logback;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;


/**
 * 在线程中加入用户信息的布局
 * @author zhangkai
 *
 */
public class ThreadUserPatternLayoutEncoder extends PatternLayoutEncoder {

	
	static {
		//加入到默认的布局管理器中
		PatternLayout.defaultConverterMap.put("bususerinfo", ThreadUserConverter.class.getName());
	}
	
}
