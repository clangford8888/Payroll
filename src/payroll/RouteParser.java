/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author Casey
 */

/**
 * 
 * THIS WHOLE CLASS PROBABLY NEEDS TO BE REDESIGNED TO HANDLE .XLS/.XLSX
 * WITHOUT HAVING SEPARATE METHODS!!!
 * 
 */
public class RouteParser {
    
    // Declare variables
    private File inputFile;
    private FileInputStream inputStream;
    private HSSFWorkbook workbookH;
    private HSSFSheet sheetH;
    private XSSFWorkbook workbookX;
    private XSSFSheet sheetX;
    private int lastRow, lastColumn;
    private String[] routeTitles;
    
    // Constructor takes in an input file to parse
    public RouteParser(File input){
         
        inputFile = input;
        
        try{
            inputStream = new FileInputStream(inputFile);
        } catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "Input file not found!");
        }      
    }
    
    // Check which methods will be needed to parse the File
    public String checkFileType(){
        
        return "";
    }
    
    /*
        Purpose: Ensure the HSSF file has not changed format
        Action: Display exception and stop parsing of file if not in correct format
    */
    public boolean isValidFileFormat(HSSFWorkbook inWorkbook){
        return true;
    }
    
    /*
        Purpose: Ensure the XSSF is in the standard format at time of program design
        Action: Display exception and stop parsing of file if not in correct format
    
    */
    public boolean isValidFileFormat(XSSFWorkbook inWorkbook){
        
        // String array representation of expected titles in order
        routeTitles = new String[]{"Job Status (ETAdirect)", "Time Slot", 
            "Job Start - End Time", "Activity", "Name", "Work Order Number",
            "Primary Phone", "Secondary Phone", "Service Address", "City", "ZIP",
            "Account Number", "Traveling Time", "Service Codes",
            "Standard Duration", "Calculated Skills", "Connectivity on Premise",
            "Broadband Connection Established", "Phone Connection Established",
            "Reason for not connecting", "There Is No Broadband or Phone Connectivity",
            "Points"
        };
        
        // First row of the workbook will contain the titles
        int currentIndex = 0;
        XSSFSheet currentSheet = inWorkbook.getSheetAt(0);
        XSSFRow currentRow = currentSheet.getRow(0);
        
        // Traverse title row and return false if titles do not match (format
        //      has changed)
        for(Cell cell: currentRow ){
            if(!cell.getStringCellValue().equals(routeTitles[currentIndex])){
                System.out.println(cell.getStringCellValue());
                return false;
            }
            currentIndex++;
        }
        
        return true;
    }
    
    public void parseFileHSSF(){
        
    }
    
    
    /*
    Purpose: Traverse .xlsx file and store all relevant route information in db
    */
    public void parseFileXSSF() throws IOException{
        
        workbookX = new XSSFWorkbook(inputStream);
        sheetX = workbookX.getSheetAt(0);
        lastRow = sheetX.getLastRowNum();
        XSSFRow currentRow;
        XSSFCell currentCell;

        if(this.isValidFileFormat(workbookX)){
        
            // Start at 2nd row (0 indexed) because 1st row is titles
            for(int i = 1; i < lastRow+1; i++){
                
                currentRow = sheetX.getRow(i);
                // Check to see if the row conatins a valid job to be stored
                if(isValidJob(currentRow)){
                    
                    // Don't need to iterate over whole row. Just doing this for testing purposes! ******
                    for(Cell cell: currentRow){
                        System.out.print(cell.getStringCellValue() + " ");
                    }
                }
                System.out.println("ROW INDEX IS: " + i);
            }
        }
        
        // NEED ELSE{ * IF IT IS NOT RIGHT FORMAT!!
    }
    
    /*
    Purpose: Check row to see if it needs to be processed
    Action: Return true if data should be added to database
    */
    private static boolean isValidJob(XSSFRow inRow) throws IOException{
        
        XSSFCell jobStatusCell = inRow.getCell(0);
        XSSFCell activityCell = inRow.getCell(3);
        String jobStatus = jobStatusCell.getStringCellValue();
        String activity = activityCell.getStringCellValue();

        // Only store jobs that were completed, not cancelled or suspended
        if(!jobStatus.equals("completed")){
            return false;
        }
        // Only store actual work ordres, do not store random activities
        else if(!Arrays.asList("NC", "CH", "RS", "TC").contains(activity)){
            return false;
        }
        
        return true;
        
    }
    
    public XSSFWorkbook getXSSFWorkbook(){
     
        return workbookX;
    }
}