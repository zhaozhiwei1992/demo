package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {

	private static final Pattern pattern = Pattern.compile("\\[(.*?)]");

	public static void main(String args[]) {

		// 匹配中括号中内容
		String str = "编码code[ 88228822 ]; 或者唯一标识[  604CF417E66F7EFEE0530603A8C02444]已存在";
		Matcher matcher = pattern.matcher(str);

		final StringBuffer buffer = new StringBuffer();
		while (matcher.find()) {
			final String matcherStr = matcher.group(1).trim();
			System.out.println(matcherStr);
			buffer.append("'").append(matcherStr).append("',");
		}
		System.out.println(buffer.toString());

		String updateSendStatus = "update xx set message = ? where status = ? and id in (" + buffer.toString().substring(0, buffer.length() - 1)+ ")";
		System.out.println(updateSendStatus);

	}

}