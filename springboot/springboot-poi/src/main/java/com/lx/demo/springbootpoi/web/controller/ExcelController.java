package com.lx.demo.springbootpoi.web.controller;

import com.lx.demo.springbootpoi.domain.THmStandard;
import com.lx.demo.springbootpoi.util.ExcelUtils;
import com.lx.demo.springbootpoi.web.controller.contrant.AjaxJson;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * 模拟excel导入导出
 */
@Controller
public class ExcelController {
    /**
     * excel导出
     * 使用poi实现, 必要时可灵活通过数据库配置导出
     * 浏览器访问: http://127.0.0.1:8080/exportExcel
     * @param response
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response){

        final List<THmStandard> all = Collections.emptyList();

        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            //创建sheet
            HSSFSheet sheet1 = workbook.createSheet("家庭成员信息");
            //创建row信息
            HSSFRow row = sheet1.createRow(0);
            //创建单元格头标
            row.createCell(0).setCellValue("农户编号");
            row.createCell(1).setCellValue("户主姓名");
            row.createCell(2).setCellValue("户主身份证号");
            row.createCell(3).setCellValue("家庭成员姓名");
            row.createCell(4).setCellValue("成员身份证号");


            //数据填入
            for(THmStandard map: all){
                int lastRowNum = sheet1.getLastRowNum();
                HSSFRow lastRow = sheet1.createRow(lastRowNum + 1);
                lastRow.createCell(0).setCellValue(map.getFarmercode());
                lastRow.createCell(1).setCellValue(map.getName());
                lastRow.createCell(2).setCellValue(map.getCitid());
            }

            //设置文件名
            String fileName = "sysParmaTable.xls";
            //设置请求返回头部信息
            response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("filename", URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * @description 导出模板
     * @param response
     * @return
     */
    @RequestMapping(value = "exportTemplate")
    public void exportTemplate(@RequestParam(defaultValue="0")String importtype, HttpServletResponse response) {
        // 这里考虑在内存中通过反射生产模板,但是每次变化模板估计被现场骂死
        String path="template/sysUserTemplate.xls";
        FileInputStream fis=null;
        try {
            File file = new File(path);
            //取得文件名。
            String fileName = file.getName();
            //以流的形式下载文件。
            fis = new FileInputStream(path);
            //添加列
            Workbook wb = WorkbookFactory.create(fis);

            fis.close();
            //清空response
            response.reset();
            //设置response的Header
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/ms-excel;");
            wb.write(toClient);
            toClient.flush();
            toClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 导入excel
     * @param request
     * @return
     */
    @RequestMapping(value = "/doImportExcel")
    @ResponseBody
    public AjaxJson doImportExcel(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        InputStream fis = null;
        try {
            // 接收excel
            MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
            mulRequest.setCharacterEncoding("UTF-8");
            Map<String, MultipartFile> fileMap = mulRequest.getFileMap();
            final List<THmStandard> standards = new ArrayList<>();

            // 重复校验
            Map<String, Integer> idMap = new HashMap<String, Integer>();

            for (Map.Entry<String, MultipartFile> entry : fileMap.entrySet()) {
                MultipartFile mulFile = entry.getValue();
                fis = mulFile.getInputStream();
                Workbook workbook = WorkbookFactory.create(fis);
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        //第1行是标题行
                        continue;
                    }

                    String id = "";
                    String farmerCode = ExcelUtils.getCellValue(row.getCell(0)).trim();
                    String hhname = ExcelUtils.getCellValue(row.getCell(1)).trim();
                    String hhcitid = ExcelUtils.getCellValue(row.getCell(2)).trim();
                    String name = ExcelUtils.getCellValue(row.getCell(3)).trim();
                    String citid = ExcelUtils.getCellValue(row.getCell(4)).trim();

                    idMap.put(id, (row.getRowNum() + 1));

                    final THmStandard tHmStandard = new THmStandard();
                    tHmStandard.setFarmercode(farmerCode);
                    tHmStandard.setName(name);
                    tHmStandard.setCitid(citid);

                    standards.add(tHmStandard);
                }

            }

            //批量保存
            System.out.println(standards);

        } catch (Exception e) {
            e.printStackTrace();
            j.setSuccess(false);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return j;
    }

    /**
     * excel导出
     * 使用poi实现, 必要时可灵活通过数据库配置导出
     * 浏览器访问: http://127.0.0.1:8080/exportExcel
     * @param response
     */
    @RequestMapping("/v2/exportExcel")
    public void exportExcelMultiHead(HttpServletResponse response){

        List<Map<String, Object>> colList = new ArrayList<>();
        colList.add(new HashMap<String, Object>() {{
            put("field_code", "REPORT_ITEM_CODE");
            put("field_name", "项目");
            put("head", new String[]{});
        }});
        colList.add(new HashMap<String, Object>() {{
            put("field_code", "JE1");
            put("field_name", "预算数");
            put("head", new String[]{});
        }});
        colList.add(new HashMap<String, Object>() {{
            put("field_code", "JE2");
            put("field_name", "小计");
            put("head", new String[]{"上级专项调整数","变动项目"});
        }});
        colList.add(new HashMap<String, Object>() {{
            put("field_code", "JE3");
            put("field_name", "企业上下划");
            put("head", new String[]{"上级专项调整数","变动项目"});
        }});
        colList.add(new HashMap<String, Object>() {{
            put("field_code", "JE4");
            put("field_name", "其它");
            put("head", new String[]{"上级专项调整数","变动项目"});
        }});
        colList.add(new HashMap<String, Object>() {{
            put("field_code", "JE5");
            put("field_name", "增加减少预算指标");
            put("head", new String[]{"变动项目"});
        }});
        colList.add(new HashMap<String, Object>() {{
            put("field_code", "JE6");
            put("field_name", "调整预算数");
            put("head", new String[]{});
        }});

        List<Map<String, Object>> dataList = new ArrayList<>();
        dataList.add(new HashMap<String, Object>(){{
            put("JE1", "6.66");
            put("JE2", "6.67");
            put("JE3", "6.68");
        }});
        dataList.add(new HashMap<String, Object>(){{
            put("JE1", "7.66");
            put("JE2", "8.67");
            put("JE3", "9.68");
        }});


        try {

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");

            // 获取最大表头高度
            int maxLevels = colList.stream().mapToInt(e -> ((String[]) e.getOrDefault("head", new String[]{})).length + 1).max().orElse(0);

            // 按行填空
            for (int level = 0; level < maxLevels; level++) {
                Row row = sheet.createRow(level);
                int columnIndex = 0;
                for (Map<String, Object> colDefinition : colList) {
                    String[] heads = (String[]) colDefinition.getOrDefault("head", new String[]{});
                    if (level < heads.length) {
                        Cell cell = row.createCell(columnIndex);
                        cell.setCellValue(heads[heads.length - level - 1]);
                    } else if (level == maxLevels - 1) {
                        // 末尾行
                        Cell cell = row.createCell(columnIndex);
                        cell.setCellValue((String) colDefinition.get("field_name"));
                    }
                    columnIndex++;
                }
            }

            // 合并单元格 - 横向
            for (int i = 0; i < maxLevels - 1; i++) {
                int startColumn = -1;
                String prevHead = null;
                for (int columnIndex = 0; columnIndex <= colList.size(); columnIndex++) {
                    String currHead = null;
                    if (columnIndex < colList.size() && ((String[]) colList.get(columnIndex).getOrDefault("head", new String[]{})).length > 0) {
                        final String[] heads = (String[]) colList.get(columnIndex).get("head");
                        if((heads.length - 1 - i) > -1){
                            currHead = heads[heads.length - 1 - i];
                        }
                    }

                    if (currHead != null && currHead.equals(prevHead)) {
                        continue;
                    }

                    if (prevHead != null && startColumn != columnIndex - 1) {
                        sheet.addMergedRegion(new CellRangeAddress(i, i, startColumn, columnIndex - 1));
                    }

                    startColumn = columnIndex;
                    prevHead = currHead;
                }
            }

            // 合并 - 纵向
            // 按列处理, maxHeader.size - header.size就是合并几个
            for (int columnIndex = 0; columnIndex < colList.size(); columnIndex++) {
                final String[] heads = (String[]) colList.get(columnIndex).getOrDefault("head", new String[]{});
                int num = maxLevels - heads.length - 1;
                if(num > 0){
                    sheet.addMergedRegion(new CellRangeAddress(maxLevels - 1 - num , maxLevels - 1, columnIndex, columnIndex));
                }
            }

            //数据填入
//            for(THmStandard map: all){
//                int lastRowNum = sheet1.getLastRowNum();
//                HSSFRow lastRow = sheet1.createRow(lastRowNum + 1);
//                lastRow.createCell(0).setCellValue(map.getFarmercode());
//                lastRow.createCell(1).setCellValue(map.getName());
//                lastRow.createCell(2).setCellValue(map.getCitid());
//            }

            //设置文件名
            String fileName = "sysParmaTable.xls";
            //设置请求返回头部信息
            response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
            response.addHeader("filename", URLEncoder.encode(fileName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void mergeSameHeaders(Sheet sheet, List<Map<String, Object>> colList, int level) {
        String lastHeader = null;
        int startCol = -1;

        for (int columnIndex = 0; columnIndex <= colList.size(); columnIndex++) {
            String currentHeader = null;
            if (columnIndex < colList.size()) {
                String[] heads = (String[]) colList.get(columnIndex).getOrDefault("head", new String[]{});
                if (level < heads.length) {
                    currentHeader = heads[level];
                } else if (level == heads.length) {
                    currentHeader = (String) colList.get(columnIndex).get("field_name");
                }
            }

            if (currentHeader != null && currentHeader.equals(lastHeader)) {
                continue;
            }

            if (lastHeader != null && startCol != columnIndex - 1) {
                sheet.addMergedRegion(new CellRangeAddress(level, level, startCol, columnIndex - 1));
            }

            startCol = columnIndex;
            lastHeader = currentHeader;
        }
    }
}
