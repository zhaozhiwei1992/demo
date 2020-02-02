package com.lx.demo.springbootpoi.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * excel工具类
 * @author hyq
 * @date Dec 25, 2014
 */
public class ExcelUtils {
	/**
	 * 获取单元格的str值
	 * @param cell
	 * @return
	 */
	public static String getCellValue1(Cell cell) {// 获取单元格数据内容为字符串类型的数据
        String strCell = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
	        case Cell.CELL_TYPE_FORMULA:
	        	//此处判断使用公式生成的字符串有问题，因为HSSFDateUtil.isCellDateFormatted(cell)判断过程中
	        	//cell.getNumericCellValue();方法会抛出java.lang.NumberFormatException异常 
	        	/*try{
	        		if (HSSFDateUtil.isCellDateFormatted(cell)) {  
		                Date date = cell.getDateCellValue();  
		                DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		                strCell = dateTimeFormat.format(date);
		                break;  
		             }else{  
		                strCell = String.valueOf(cell.getNumericCellValue());  
		             }  
	        		System.out.println(cell.getCellFormula());
	        	}catch(IllegalStateException e) {  
	                strCell = String.valueOf(cell.getRichStringCellValue());  
	            } */
	        	strCell = cell.getCellFormula();
	            break; 
	        case Cell.CELL_TYPE_STRING:
	            strCell = cell.getStringCellValue().trim();
	            //strCell = strCell.replaceAll(",", "");							// 替换","
	            break;
	        case Cell.CELL_TYPE_NUMERIC:
	            if (HSSFDateUtil.isCellDateFormatted(cell)) {
	            	Date date = cell.getDateCellValue();  
	                DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                strCell = dateTimeFormat.format(date);
	                break;
	            } else {
	                strCell = String.valueOf(cell.getNumericCellValue());
	                //strCell = strCell.replaceAll(",", "");						// 替换","
	                break;
	            }
	        case Cell.CELL_TYPE_BOOLEAN:
	            strCell = String.valueOf(cell.getBooleanCellValue());
	            break;
	        case Cell.CELL_TYPE_BLANK:
	            strCell = "";
	            break;
	        default:
	            strCell = "";
	            break;
        }
        return strCell;
    }
	/**
	 * 获取单元格的str值
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {// 获取单元格数据内容为字符串类型的数据
        String strCell = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
	        case Cell.CELL_TYPE_FORMULA:
	        	strCell = cell.getCellFormula();
	            break; 
	        case Cell.CELL_TYPE_STRING:
	            strCell = cell.getStringCellValue().trim();
	            strCell = strCell.replaceAll(",", "");							// 替换","
	            break;
	        case Cell.CELL_TYPE_NUMERIC:
	            if (HSSFDateUtil.isCellDateFormatted(cell)) {
	            	Date date = cell.getDateCellValue();  
	                DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                strCell = dateTimeFormat.format(date);
	                break;
	            } else {
					cell.setCellType(CellType.STRING);
					strCell = cell.getStringCellValue();
	                strCell = strCell.replaceAll(",", "");						// 替换","
	                break;
	            }
	        case Cell.CELL_TYPE_BOOLEAN:
	            strCell = String.valueOf(cell.getBooleanCellValue());
	            break;
	        case Cell.CELL_TYPE_BLANK:
	            strCell = "";
	            break;
	        default:
	            strCell = "";
	            break;
        }
        return strCell;
    }
	
	
	/**
	 * 获取sheet中所有合并了的单元格
	 * @param sheet
	 * @return
	 */
	public static List<CellRangeAddress> getMergerdRegionBySheet(Sheet sheet){
		List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		
		//获取sheet中所有合并单元格的数量
		int sheetMergerCount = sheet.getNumMergedRegions();
		//遍历合并单元格
		for(int i=0;i<sheetMergerCount;i++){
			//获得合并单元格加入list中
			CellRangeAddress  ca = sheet.getMergedRegion(i);
			list.add(ca);
		}
		return list;
	}
	
	/**
	 * 判断是否是合并单元格，如果是，则获取合并的行数
	 * @param cell 当前单元格
	 * @param list 所有合并单元格
	 * @return
	 */
	public static int getMergerRowCount(Cell cell,List<CellRangeAddress> list){
		int rowCount = 0;
		for(CellRangeAddress ca : list){
			int firstC = ca.getFirstColumn();
			int lastC = ca.getLastColumn();
			int firstR = ca.getFirstRow();
			int lastR = ca.getLastRow();
			if(cell.getColumnIndex()<=lastC && cell.getColumnIndex()>=firstC){
				if(cell.getRowIndex()<=lastR && cell.getRowIndex()>=firstR){
					//则是合并单元格
					rowCount = lastR - firstR;	
				}
			}
		}
		return rowCount;
	}
}
