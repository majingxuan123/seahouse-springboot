package com.seahouse.compoment.utils.excelutils;

import com.seahouse.compoment.utils.jsonutils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <ul>
 * <li>文件名称: ExcelUtils</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/4/17 0017</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author majx
 * @version 1.0.0
 */
//@Component
public class ExcelUtils {

//
//    public static void main(String[] args) throws Exception {
//        File file = new File("d:/老年病医院商户号.xlsx");
//        File file2 = new File("d:/老年病医院商户号2222.xls");
//        List<List<String>> list = readXlsxFile(file);
//
//        String str = excelListsToJson(list);
//
//        excelJsonToLists(str);
//
//    }

    /**
     * 读取EXCEL    只能读取XLSX格式
     *
     * @param file
     * @return 返回的list 中的第一个list 是excel的列表字段名
     * 之后的list分别是每一行的数据
     * @throws IOException
     */
    public static List<List<String>> readXlsxFile(File file) throws IOException {
        List<List<String>> lists = new ArrayList<List<String>>();
        List<String> columnsList = new ArrayList<String>();
        InputStream is = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
        // 获取每一个工作薄
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            // 获取当前工作薄的每一行
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow != null) {
                    Iterator<Cell> it = xssfRow.cellIterator();
                    //如果是第一行的话 加入columnsList      第一行的元素都是名字
                    if (rowNum == 0) {
                        while (it.hasNext()) {
                            columnsList.add(it.next().toString());
                        }
                        lists.add(columnsList);
                    } else {
                        List<String> list = new ArrayList<String>();
                        //其他行就是数据
                        while (it.hasNext()) {
                            list.add(it.next().toString());
                        }
                        lists.add(list);
                    }
                }
            }
        }
        return lists;
    }


    //转换数据格式
//    private String getValue(XSSFCell xssfRow) {
//        if (xssfRow.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//            return String.valueOf(xssfRow.getBooleanCellValue());
//        } else if (xssfRow.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//            return String.valueOf(xssfRow.getNumericCellValue());
//        } else {
//            return String.valueOf(xssfRow.getStringCellValue());
//        }
//    }

    /**
     * 读取EXCEL    只能读取XLS格式
     *
     * @param file
     * @return 返回的list 中的第一个list 是excel的列表字段名
     * 之后的list分别是每一行的数据
     * @throws IOException
     */
    public static List<List<String>> readXlsFile(File file) throws IOException {
        List<List<String>> lists = new ArrayList<List<String>>();
        List<String> columnsList = new ArrayList<String>();
        InputStream is = new FileInputStream(file);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // 获取每一个工作薄
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 获取当前工作薄的每一行
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    Iterator<Cell> it = hssfRow.cellIterator();
                    //如果是第一行的话 加入columnsList      第一行的元素都是名字
                    if (rowNum == 0) {
                        while (it.hasNext()) {
                            columnsList.add(it.next().toString());
                        }
                        lists.add(columnsList);
                    } else {
                        List<String> list = new ArrayList<String>();
                        //其他行就是数据
                        while (it.hasNext()) {
                            list.add(it.next().toString());
                        }
                        lists.add(list);
                    }
                }
            }
        }
        return lists;
    }

    // 转换数据格式
//    private String getValue(HSSFCell hssfCell) {
//        if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//            return String.valueOf(hssfCell.getBooleanCellValue());
//        } else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//            return String.valueOf(hssfCell.getNumericCellValue());
//        } else {
//            return String.valueOf(hssfCell.getStringCellValue());
//        }
//    }

    /**
     * 新建一个excel
     *
     * @param dataList      list数据
     * @param finalXlsxPath 目标文件
     * @throws IOException
     */
    public static void createExcel(List<List<String>> dataList, String finalXlsxPath) throws IOException {
        FileOutputStream fileOut = null;
        try {
            Workbook wb = new XSSFWorkbook();
            //这个是循环出一行
            Sheet sheet = wb.createSheet("title");
            Row row = null;
            for (int i = 0; i < dataList.size(); i++) {
                //获取一行的数据
                List<String> rowList = dataList.get(i);
                if (rowList != null) {
                    //创建一行
                    row = sheet.createRow(i);
                }
                //循环单元格
                for (int j = 0; j < rowList.size(); j++) {
                    if (!StringUtils.isEmpty(rowList.get(j))) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue(rowList.get(j));
                    }
                }
            }
            fileOut = new FileOutputStream(finalXlsxPath);
            wb.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.flush();
                    fileOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("新建excel成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     *
     * @param
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException {
        Workbook wb = null;
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            //Excel&nbsp;2003
            if (file.getName().endsWith("xls")) {
                wb = new HSSFWorkbook(in);
                // Excel 2007/2010
            } else if (file.getName().endsWith("xlsx")) {
                wb = new XSSFWorkbook(in);
            }
            return wb;
        } else {
            if (file.getName().endsWith("xls")) {
                wb = new HSSFWorkbook();
                FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
                wb.write(fileOut);
                fileOut.close();
            } else if (file.getName().endsWith("xlsx")) {
                wb = new XSSFWorkbook();
                FileOutputStream fileOut = new FileOutputStream(file.getAbsolutePath());
                wb.write(fileOut);
                fileOut.close();
            }
            return wb;
        }
    }


    /**
     * 将一个读写XML的list转化为json
     *
     * @param lists
     * @return
     */
    public static String excelListsToJson(List<List<String>> lists) {
        String str = JsonUtil.convertObjectToJson(lists);
        return str;
    }

    /**
     * 将json转化为用于新建excel的list<list<string> 格式
     * @param jsonStr
     * @return
     */
    public static List<List<String>> excelJsonToLists(String jsonStr) {
        List<List<String>> lsits = new ArrayList<List<String>>();
        List<List> l = JsonUtil.parseJsonToObject(jsonStr, List.class);
        return lsits;
    }

}
