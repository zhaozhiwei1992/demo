package com.lx.demo.springbootpoi.web.controller;

import com.lx.demo.springbootpoi.domain.THmStandard;
import com.lx.demo.springbootpoi.util.ExcelUtils;
import com.lx.demo.springbootpoi.web.controller.contrant.AjaxJson;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.StringUtil;
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
}
