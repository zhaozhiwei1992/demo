package com.lx.demo.springboot.markdown.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlTableParser {

    public static TableData parseHtmlTable(String htmlTable) {
        Document doc = Jsoup.parse(htmlTable, "", Parser.xmlParser());
        TableData tableData = new TableData();

        // 1. 解析表头 (thead)
        Elements headerRows = doc.select("thead tr");
        for (Element headerRow : headerRows) {
            List<String> headerCells = new ArrayList<>();
            Elements thElements = headerRow.select("th"); // 获取th标签
            for (Element th : thElements) {
                headerCells.add(th.text());
            }
            tableData.addHeaders(headerCells);
        }

        // 2. 解析表格体数据 (tbody)
        Elements bodyRows = doc.select("tbody tr");
        for (Element bodyRow : bodyRows) {
            List<String> rowCells = new ArrayList<>();
            Elements tdElements = bodyRow.select("td"); // 获取td标签
            for (Element td : tdElements) {
                rowCells.add(td.text());
            }
            tableData.addRow(rowCells);
        }
        return tableData;
    }

    // 用一个简单的类来存储解析后的表格数据
    public static class TableData {
        private List<List<String>> headers = new ArrayList<>(); // 支持多行表头
        private List<List<String>> rows = new ArrayList<>();

        public void addHeaders(List<String> headerRow) {
            headers.add(headerRow);
        }
        public void addRow(List<String> row) {
            rows.add(row);
        }
        public List<List<String>> getHeaders() { return headers; }
        public List<List<String>> getRows() { return rows; }
    }
}