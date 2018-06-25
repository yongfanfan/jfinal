package com.haitai.haitaitv.component.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;

public class Poi {

    private Sheet sheet; // 表格类实例
    LinkedList[] result; // 保存每个单元格的数据 ，使用的是一种链表数组的结构

    private static final String EXTENSION_XLS = "xls";
    private static final String EXTENSION_XLSX = "xlsx";

    /***
     * <pre>
     * 取得Workbook对象(xls和xlsx对象不同,不过都是Workbook的实现类) 
     *   xls:HSSFWorkbook 
     *   xlsx：XSSFWorkbook 
     * @param filePath 
     * @return 
     * @throws IOException
     * </pre>
     */
    private Workbook getWorkbook(String filePath) throws IOException {
        Workbook workbook = null;
        InputStream is = new FileInputStream(filePath);
        if (filePath.endsWith(EXTENSION_XLS)) {
            workbook = new HSSFWorkbook(is);
        } else if (filePath.endsWith(EXTENSION_XLSX)) {
            workbook = new XSSFWorkbook(is);
        }
        return workbook;
    }

    /**
     * 文件检查
     * 
     * @param filePath
     * @throws FileNotFoundException
     * @throws FileFormatException
     */
    private void preReadCheck(String filePath) throws FileNotFoundException,
            FileFormatException {
        // 常规检查
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException("传入的文件不存在：" + filePath);
        }

        if (!(filePath.endsWith(EXTENSION_XLS) || filePath
                .endsWith(EXTENSION_XLSX))) {
            throw new FileFormatException("传入的文件不是excel");
        }
    }

    // 读取excel文件，创建表格实例
    public void loadExcel(String filePath) {
        try {
            preReadCheck(filePath);
            Workbook workBook = getWorkbook(filePath);
            sheet = workBook.getSheetAt(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           
        }
    }

    // 获取单元格的值
    private String getCellValue(Cell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            // 判断单元格数据的类型，不同类型调用不同的方法
            switch (cell.getCellType()) {
            // 数值类型
            case Cell.CELL_TYPE_NUMERIC:
                // 进一步判断 ，单元格格式是日期格式
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = formatter.formatCellValue(cell);
                } else {
                    // 数值
                    double value = cell.getNumericCellValue();
                    int intValue = (int) value;
                    cellValue = value - intValue == 0 ? String
                            .valueOf(intValue) : String.valueOf(value);
                }
                break;
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            // 判断单元格是公式格式，需要做一种特殊处理来得到相应的值
            case Cell.CELL_TYPE_FORMULA: {
                try {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    cellValue = String.valueOf(cell.getRichStringCellValue());
                }

            }
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR:
                cellValue = "";
                break;
            default:
                cellValue = cell.toString().trim();
                break;
            }
        }
        return cellValue.trim();
    }

    // 初始化表格中的每一行，并得到每一个单元格的值
    public void init() {
        int rowNum = sheet.getLastRowNum() + 1;
        result = new LinkedList[rowNum];
        for (int i = 0; i < rowNum; i++) {
            Row row = sheet.getRow(i);
            // 每有新的一行，创建一个新的LinkedList对象
            result[i] = new LinkedList();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                // 获取单元格的值
                String str = getCellValue(cell);
                // 将得到的值放入链表中
                result[i].add(str);
            }
        }
    }

    // 控制台打印保存的表格数据
    public LinkedList[] show() {
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].size(); j++) {
                System.out.print(result[i].get(j) + "\t");
            }
            System.out.println();
        }
        return result;
    }

    public static void main(String[] args) {

    }

}