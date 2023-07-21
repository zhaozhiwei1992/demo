package com.lx.demo.springbootel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Title: CellRangeExpander
 * @Package com/lx/demo/springbootel/service/CellRangeExpander.java
 * @Description: 单元格数据拆分, 如AD9:AD15 A8,D8 AE5:AE10
 * @author zhaozhiwei
 * @date 2023/7/21 下午5:03
 * @version V1.0
 */
public class CellRangeExpander {
    public static List<String> expandFormula(String formula) {
        List<String> expandedCells = new ArrayList<>();

        // 使用正则表达式匹配 SUM(AD7:FD7) 形式
        Pattern pattern = Pattern.compile("SUM\\((.*?)\\)");
        Matcher matcher = pattern.matcher(formula);

        // 提取所有 SUM 函数中的单元格范围并展开
        while (matcher.find()) {
            String cellRange = matcher.group(1);
            expandedCells.addAll(expandCellRange(cellRange));
        }

        // 使用正则表达式匹配 A7:D7 形式
        pattern = Pattern.compile("[A-Z]+\\d+:[A-Z]+\\d+");
        matcher = pattern.matcher(formula);

        // 提取所有单元格范围并展开
        while (matcher.find()) {
            String cellRange = matcher.group();
            expandedCells.addAll(expandCellRange(cellRange));
        }

        return expandedCells;
    }

    public static List<String> expandCellRange(String cellRange) {
        List<String> expandedCells = new ArrayList<>();

        // 分割参数，使用冒号":"作为分隔符
        String[] rangeParts = cellRange.split(":");
        if (rangeParts.length != 2) {
            throw new IllegalArgumentException("Invalid cell range format: " + cellRange);
        }

        String startCell = rangeParts[0];
        String endCell = rangeParts[1];

        // 提取起始单元格的列和行信息
        String startColumn = startCell.replaceAll("\\d", "");
        int startRow = Integer.parseInt(startCell.replaceAll("\\D", ""));

        // 提取结束单元格的列和行信息
        String endColumn = endCell.replaceAll("\\d", "");
        int endRow = Integer.parseInt(endCell.replaceAll("\\D", ""));

        // 展开单元格范围并添加到列表中
        for (int row = startRow; row <= endRow; row++) {
            for (int col = columnToNumber(startColumn); col <= columnToNumber(endColumn); col++) {
                expandedCells.add(numberToColumn(col) + row);
            }
        }

        return expandedCells;
    }

    public static int columnToNumber(String column) {
        // AA, BB等转数字方便计算
        int result = 0;
        for (int i = 0; i < column.length(); i++) {
            result = result * 26 + (column.charAt(i) - 'A' + 1);
        }
        return result;
    }

    public static String numberToColumn(int number) {
        StringBuilder result = new StringBuilder();
        while (number > 0) {
            int remainder = (number - 1) % 26;
            result.insert(0, (char) ('A' + remainder));
            number = (number - 1) / 26;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String formula = "SUM(AD7:AD15) + A7:D7 + AE5:AH5";

//        List<String> expandedCells = expandFormula(formula);

        System.out.println("Expanded cells for " + formula + ":");
//        System.out.println(expandedCells);
        final String s = numberToColumn(28);
        System.out.println(s);
        System.out.println(columnToNumber("AB"));
    }
}