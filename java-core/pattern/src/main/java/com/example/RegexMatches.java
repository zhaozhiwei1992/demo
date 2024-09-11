package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {

	private static final Pattern pattern = Pattern.compile("\\[(.*?)]");

	public static void main(String args[]) {

//		test1();
//		test2();
		test3();

	}

	private static void test3() {

		String formula = "#scope(#dwlevel=='04',#czybggyssrjslr['czybggyssrjslr0001:je1']<=10000+pay@202306@ysszyb2022@政府性基金预算收入合计@1)";
		Pattern pattern = Pattern.compile("pay@(\\d{6})@([^!]+)@([^!]+)@\\d");

		final Matcher matcher = pattern.matcher(formula);
		while (matcher.find()){
			final String group = matcher.group();
			System.out.println(group);
		}
		if(matcher.matches()){
			System.out.println(matcher.group(1));
			System.out.println(matcher.group(2));
			System.out.println(matcher.group(3));
		}
	}

	private static void test2() {
//		String formula = "AX8=L05!B78";
//		String formula = "BQ8==CompareSuperiorLowCheck!BQ8";
		String formula = "BE8=L05!B118-D119";
//		String formula = "SUM(BQ8:BR8)";
		int index = 9;
		// 提取计算公式或者校验公式两边单元格表达, 数字全部增加游标值
		Pattern formulaPattern = Pattern.compile("[A-Za-z]+[0-9]+![A-Za-z]+[0-9]+|[A-Za-z]+[0-9]+");

		Pattern coorDinatePattern = Pattern.compile("([A-Za-z]+)([0-9]+)");

		// 公式可能包含!,这种一般不需要增加,其它公式取得公式中任意一个符合条件的单元作为基准行即可
		// 使用Matcher查找并替换所有匹配的数字
		Matcher matcher = formulaPattern.matcher(formula);
		StringBuffer sb = new StringBuffer();
		int baseRowNum = 0;
		while (matcher.find()) {
			// 提取数字，转换为整数，增加i，然后转换回字符串
			String matchItem = matcher.group();
			if(!matchItem.contains("!")){
				// 第一个不带!号的作为基准行, 计算公式目前不全，有问题再说
				// 计算 SUM(BQ8:BR8)
				// 校验 BQ8==CompareSuperiorLowCheck!BQ8  AU8==SUM(AV8:BE8)
				// 取数 $FDB$BE8=L05!B118-L05!D119 对于这种只修改BE8
				Matcher matcher2 = coorDinatePattern.matcher(matchItem);
				if(matcher2.matches()){
					String curCoordinateNum = matcher2.group(2);
					if(matcher2.matches() && baseRowNum < 1){
						baseRowNum = Integer.parseInt(curCoordinateNum);
					}
					if(!curCoordinateNum.equals(String.valueOf(baseRowNum))){
						matcher.appendReplacement(sb, matchItem);
						continue;
					}
					String curCoordinateStr = matcher2.group(1);
					int incrementedNumber = baseRowNum + index;
					matcher.appendReplacement(sb, curCoordinateStr + incrementedNumber);
				}else{
					matcher.appendReplacement(sb, matchItem);
				}
			}else{
				matcher.appendReplacement(sb, matchItem);
			}
		}
		matcher.appendTail(sb);
		System.out.println("最终: " + sb.toString());
	}

	private static void test1() {
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