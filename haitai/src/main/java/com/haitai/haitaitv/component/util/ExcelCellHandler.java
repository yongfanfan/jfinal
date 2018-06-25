package com.haitai.haitaitv.component.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 自定义对某列单元格的处理，包括值、样式、宽度等
 * Created by liuzhou on 2017/1/20
 */
@Deprecated
public interface ExcelCellHandler {

    void process(Workbook workbook, Cell cell, Object value, Object item);

    ExcelCellHandler parseDoubleHandler = new ParseDoubleHandler();

    /**
     * 自动将value转为Double
     * 遇到Number直接转，遇到String尝试转
     */
    class ParseDoubleHandler implements ExcelCellHandler {
        // 单例化
        private ParseDoubleHandler() {
        }

        @Override
        public void process(Workbook workbook, Cell cell, Object value, Object item) {
            if (value == null) {
                cell.setCellValue(0);
            } else if (value instanceof Number) {
                Number number = (Number) value;
                cell.setCellValue(number.doubleValue());
            } else if (value instanceof String) {
                String str = (String) value;
                try {
                    Double dou = Double.parseDouble(str);
                    cell.setCellValue(dou);
                } catch (NumberFormatException e) {
                    cell.setCellValue(str);
                }
            }
        }
    }

}
