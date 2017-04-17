package com.wjs.common.base.util;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by panqingqing on 16/9/30.
 */
public class ExcelUtil {
    public static List<String> result = new ArrayList<String>();

    // 1，根据一个路径，读取Excel
    public static List<String> readExcelXlsx(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(is);
        for(Sheet sheet : workbook) {
            if(sheet == null) continue;
            for(int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if(row == null) continue;
                int minColIx = row.getFirstCellNum();
                int maxColIx = row.getLastCellNum();
                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                    Cell cell = row.getCell(colIx);
                    if(cell == null) continue;
                    result.add(cell.getStringCellValue().trim());
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception{
        String path = "F:\\测试.xlsx";
        readExcelXlsx(path);
        System.out.println(result);
    }

    // 2，将Excel存储进List，并放入内存
    // 3，获取一敏感词文本，与List逐一比对，如果匹配成功，以"***"替换

    /*public static void main(String[] args) throws Exception {
        String path = "/Users/panqingqing/Downloads/1.xlsx";
        *//*List<List<String>> lists = readXlsx(path);
        System.out.println(lists);*//*
        writeXlsx(path);
    }

    private static List<List<String>> readXlsx(String path) throws Exception {
        InputStream is = new FileInputStream(path);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        List<List<String>> result = new ArrayList<List<String>>();
        // 循环每一页，并处理当前循环页
        for (Sheet xssfSheet : xssfWorkbook) {
            if (xssfSheet == null) {
                continue;
            }
            // 处理当前页，循环读取每一行
            for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                Row xssfRow = xssfSheet.getRow(rowNum);
                int minColIx = xssfRow.getFirstCellNum();
                int maxColIx = xssfRow.getLastCellNum();
                List<String> rowList = new ArrayList<String>();
                for (int colIx = minColIx; colIx < maxColIx; colIx++) {
                    Cell cell = xssfRow.getCell(colIx);
                    if (cell == null) {
                        continue;
                    }
                    rowList.add(cell.toString());
                }
                result.add(rowList);
            }
        }
        return result;
    }

    public static void writeXlsx(String filePath) throws IOException {
        //工作簿
        XSSFWorkbook hssfworkbook = new XSSFWorkbook();
        //获得CreationHelper对象,这个应该是一个帮助类
        XSSFCreationHelper helper = hssfworkbook.getCreationHelper();
        //创建sheet页
        XSSFSheet hssfsheet = hssfworkbook.createSheet();
        //设置sheet名称
        hssfworkbook.setSheetName(0, "我的测试sheet");
        //取得第一行
        XSSFRow firstRow = hssfsheet.createRow(0);
        //创建第一个单元格
        XSSFCell hssfcell_0 = firstRow.createCell(0);
        //hssfcell_0.setEncoding(HSSFWorkbook.ENCODING_UTF_16);并处理乱码
        //对第一个单元格赋值
        hssfcell_0.setCellValue("名称");
        //创建第二个单元格
        XSSFCell hssfcell_1 = firstRow.createCell(1);
        hssfcell_1.setCellValue("创建日期");
        //日期单元格格式处理
        XSSFCellStyle dateCellStyle = hssfworkbook.createCellStyle();
        //m/d/yyh:mm 设置日期格式
        dateCellStyle.setDataFormat(helper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));
        dateCellStyle = setFillBackgroundColors(dateCellStyle, IndexedColors.BLACK.getIndex(), IndexedColors.YELLOW.getIndex(), dateCellStyle.SOLID_FOREGROUND);
        //设置其他标题
        firstRow.createCell(2).setCellValue("用户");
        firstRow.createCell(3).setCellValue("备注");

        //写入所有内容行
        for (int rowInt = 1; rowInt < 500000; rowInt++) {
            XSSFRow row = hssfsheet.createRow(rowInt);
            XSSFCell cell_0 = row.createCell(0);
            cell_0.setCellValue("刘伯恩");
            XSSFCell cell_1 = row.createCell(1);
            cell_1.setCellValue(new Date());
            cell_1.setCellStyle(dateCellStyle);
            XSSFCell cell_2 = row.createCell(2);
            cell_2.setCellValue("超级会员");
            XSSFCell cell_3 = row.createCell(3);
            cell_3.setCellValue("这里是备注信息");

        }
        //输出 49.
        FileOutputStream fileoutputstream = new FileOutputStream(filePath);
        hssfworkbook.write(fileoutputstream);
        fileoutputstream.close();
    }

    public static XSSFCellStyle setFillBackgroundColors(XSSFCellStyle cellStyle, short bg, short fg, short fp) {
        cellStyle.setFillBackgroundColor(bg);
        cellStyle.setFillForegroundColor(fg);
        cellStyle.setFillPattern(fp);
        return cellStyle;
    }*/

}
