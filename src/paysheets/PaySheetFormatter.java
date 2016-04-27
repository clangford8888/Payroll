/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.util.HashSet;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author Casey
 */
public class PaySheetFormatter {
    
    
    public static void formatWorkbook(HSSFWorkbook workbook){
        HSSFSheet sheet = workbook.createSheet("Sheet 1");
        addTitleRow(workbook);
    }
    
    private static void addTitleRow(HSSFWorkbook workbook){
        // Each pay sheet only uses the first sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
  
        // Create a font and set its attributes
        Font font = workbook.createFont();        
        font.setFontHeight((short)11);
        // Set the color to black (constant COLOR_NORMAL)
        font.setColor(Font.COLOR_NORMAL);
        font.setBold(true);
        
        // Create a cell style and set its properties
        CellStyle cs = workbook.createCellStyle();
        // Set the data format to the built in text format
        cs.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
        // Set the cell style to use the font created previously
        cs.setFont(font);
        
        // Create the first title row
        row = sheet.createRow(0);
        // Use the default row height (-1) is sheet default
        row.setHeight((short)-1);

        // Add the first title row's 6 cells
        for(int cellNum = 0; cellNum < 6; cellNum++){
            cell = row.createCell(cellNum);
            cell.setCellStyle(cs);
        }
        // Populate first row's values
        cell = row.getCell(0);
        cell.setCellValue("DATE");
        cell = row.getCell(1);
        cell.setCellValue("CUSTOMER");
        cell = row.getCell(2);
        cell.setCellValue("EQUIPMENT");
        cell = row.getCell(3);
        cell.setCellValue("SHS");
        cell = row.getCell(4);
        cell.setCellValue("PAY");
        cell = row.getCell(5);
        cell.setCellValue("LEP");
    }
}
