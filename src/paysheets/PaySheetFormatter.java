/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author Casey
 */
public class PaySheetFormatter {
    private static final int DATE_WIDTH = 5000;
    private static final int CUSTOMER_WIDTH = 6800;
    private static final int PAY_WIDTH = 5000;
    private static final int SERIAL_WIDTH = 6800;
    private static final int NONSERIAL_WIDTH = 7000;
    private static final int SHS_WIDTH = 7000;
    /*
        Given a workbook, this method formats the first two rows to contain
        the title information for a standard pay sheet. Assumes the workbook
        does not have a sheet created
    */
    public static void addTitleRow(HSSFWorkbook workbook){
        workbook.createSheet("Sheet 1");
        // Each pay sheet only uses the first sheet
        HSSFSheet sheet = workbook.getSheetAt(0);
        setDefaultColumnWidth(sheet);
        HSSFRow row;
        HSSFCell cell;
  
        // Create a font and set its attributes
        Font font = workbook.createFont();        
        font.setFontHeightInPoints((short)11);
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
        cell = row.getCell(PaySheet.DATE_INDEX);
        cell.setCellValue("DATE");
        cell = row.getCell(PaySheet.CUST_INDEX);
        cell.setCellValue("CUSTOMER");
        cell = row.getCell(PaySheet.PAY_INDEX);
        cell.setCellValue("PAY");
        cell = row.getCell(PaySheet.NONSERIAL_INDEX);
        cell.setCellValue("EQUIPMENT");
        cell = row.getCell(PaySheet.SERIAL_INDEX);
        cell.setCellValue("SERIALIZED");
        cell = row.getCell(PaySheet.SHS_INDEX);
        cell.setCellValue("SHS");

        // Create second title row
        row = sheet.createRow(1);
        row.setHeight((short)-1);
        // Add the cells to the row
        for(int cellNum = 0; cellNum < 3; cellNum++){
            cell = row.createCell(cellNum);
            cell.setCellStyle(cs);
        }
        // Populate the second title row's values
        cell = row.getCell(PaySheet.WO_INDEX);
        cell.setCellValue("WORK ORDER");
        cell = row.getCell(PaySheet.TYPE_INDEX);
        cell.setCellValue("TYPE");
        cell = row.getCell(PaySheet.LEP_INDEX);
        cell.setCellValue("LEP");
        
    }
    
    /**
     * Sets the default column width based on the constants defined for the
     * PaySheetFormatter class. Apache POI uses an odd system of 1/256th of a
     * character for the width.
     * 
     * @param sheet the HSSFSheet that the column width is being set for
     */
    public static void setDefaultColumnWidth(HSSFSheet sheet){
        sheet.setColumnWidth(PaySheet.DATE_INDEX, DATE_WIDTH);
        sheet.setColumnWidth(PaySheet.CUST_INDEX, CUSTOMER_WIDTH);
        sheet.setColumnWidth(PaySheet.PAY_INDEX, PAY_WIDTH);
        sheet.setColumnWidth(PaySheet.SERIAL_INDEX, SERIAL_WIDTH);
        sheet.setColumnWidth(PaySheet.NONSERIAL_INDEX, NONSERIAL_WIDTH);
        sheet.setColumnWidth(PaySheet.SHS_INDEX, SHS_WIDTH);
    }
    
    public static void addJobFormatting(HSSFWorkbook workbook, int rowIndex){
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;
        
        Font font = workbook.createFont();
        font.setBold(false);
        font.setFontHeightInPoints((short)10);
        font.setColor(Font.COLOR_NORMAL);
        
        // Create a cell style for general text
        CellStyle generalStyle = workbook.createCellStyle();
        generalStyle.setFont(font);
        generalStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
                
        // Create a cell style for dates
        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setFont(font);
        // Set the cell data format to date (0xe) is the built in format
        dateStyle.setDataFormat((short)0xe);
        
        // Create a new row at the given index
        row = sheet.createRow(rowIndex);
        // Format the first row for the new job
        for(int cellNum = 0; cellNum < 6; cellNum++){
            cell = row.createCell(cellNum);
            // Only the first cell uses the date style
            if(cellNum > 0){
                cell.setCellStyle(generalStyle);
            }
            else{
                cell.setCellStyle(dateStyle);
            }
        }
        // Create second row for the new Job at rowIndex + 1
        row = sheet.createRow(rowIndex + 1);
        for(int cellNum = 0; cellNum < 3; cellNum++){
            cell = row.createCell(cellNum);
            cell.setCellStyle(generalStyle);
        }
    }
}
