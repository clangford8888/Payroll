/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 * @author Casey
 */
public class PaymentFileFormatChecker {
    
    private File inputFile;
    private int workOrderLocation;
    private int accountNumLocation;
    private int advancedTypeLocation;
    private int dateLocation;
    private int techIDLocation;
    private int custNameLocation;
    
    private int taskTypeLocation;
    private int taskNameLocation;
    private int taskDescriptionLocation;
    
    public PaymentFileFormatChecker(){
    }
    
    public void readFileFormat(File inFile){
        this.inputFile = inFile;
        try{
            // Create new file input stream from input file
            FileInputStream fis = new FileInputStream(this.inputFile);
            // Create new workbook from input stream
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            // Get the first sheet in the workbook (zero indexed)
            HSSFSheet firstSheet = workbook.getSheetAt(0);
            // Get the first row, which contains the titles for each field
            HSSFRow firstRow = firstSheet.getRow(0);
            // Find length of list by checking last row number
            int arrayLength = firstRow.getLastCellNum();
            
            ArrayList<String> stringCellList = new ArrayList<>();
            
            Cell cell;
            String cellValue;
            // Traverse through the row
            for(int i = 0; i < arrayLength; i++){
                // Store each cell value in the list
                cell = firstRow.getCell(i);
                cellValue = cell.getStringCellValue();
                stringCellList.add(cellValue);
            }
            // Run method to set all index locations
            setIndexLocations(stringCellList);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    /** 
     * Given an ArrayList containing the first row of an input file, find and set
     * the location of each of the necessary fields to parse the file.
     * @param list 
     */
    private void setIndexLocations(ArrayList<String> list){
        int woIndex = list.indexOf("Referral ID");
        setWorkOrderLocation(woIndex);
        int accIndex = list.indexOf("Account Number");
        setAccountNumLocation(accIndex);
        int advTypeIndex = list.indexOf("Advanced Referral Type");
        setAdvancedTypeLocation(advTypeIndex);
        int dateIndex = list.indexOf("Referral Close Date");
        setDateLocation(dateIndex);
        int techIDIndex = list.indexOf("Tech ID");
        setTechIDLocation(techIDIndex);
        int custNameIndex = list.indexOf("Customer Name");
        setCustNameLocation(custNameIndex);    
        int taskTypeIndex = list.indexOf("Task Type");
        setTaskTypeLocation(taskTypeIndex);
        int taskNameIndex = list.indexOf("Task");
        setTaskNameLocation(taskNameIndex);
        int taskDescriptionIndex = list.indexOf("Task Description");
        setTaskDescriptionLocation(taskDescriptionIndex);
    }
    
    /*
        Getters for PaymentFileFormatChecker
    */
    public int getWorkOrderLocation(){
        return this.workOrderLocation;
    }
    
    public int getAccountNumLocation(){
        return this.accountNumLocation;
    }
    
    public int getAdvancedTypeLocation(){
        return this.advancedTypeLocation;
    }
    
    public int getDateLocation(){
        return this.dateLocation;
    }
    
    public int getTechIDLocation(){
        return this.techIDLocation;
    }
    
    public int getCustNameLocation(){
        return this.custNameLocation;
    }
    
    public int getTaskTypeLocation(){
        return this.taskTypeLocation;
    }
    
    public int getTaskNameLocation(){
        return this.taskNameLocation;
    }
    
    public int getTaskDescriptionLocation(){
        return this.taskDescriptionLocation;
    }
    
    /*
        Setters for PaymentFileFormatChecker
    */
    public void setWorkOrderLocation(int inLocation){
        this.workOrderLocation = inLocation;
    }
    
    public void setAccountNumLocation(int inLocation){
        this.accountNumLocation = inLocation;
    }
    
    public void setAdvancedTypeLocation(int inLocation){
        this.advancedTypeLocation = inLocation;
    }
    
    public void setDateLocation(int inLocation){
        this.dateLocation = inLocation;
    }
    
    public void setTechIDLocation(int inLocation){
        this.techIDLocation = inLocation;
    }
    
    public void setCustNameLocation(int inLocation){
        this.custNameLocation = inLocation;
    }
    
    public void setTaskTypeLocation(int inLocation){
        this.taskTypeLocation = inLocation;
    }
    
    public void setTaskNameLocation(int inLocation){
        this.taskNameLocation = inLocation;
    }
    
    public void setTaskDescriptionLocation(int inLocation){
        this.taskDescriptionLocation = inLocation;
    }
    
    @Override
    public String toString(){
        String str =    "WO Position:\t" + this.workOrderLocation + "\n" +
                        "Acc Position:\t" + this.accountNumLocation + "\n" +
                        "Type Position:\t" + this.advancedTypeLocation + "\n" +
                        "Date Position:\t" + this.dateLocation + "\n" +
                        "TID Position:\t" + this.techIDLocation + "\n" +
                        "Cust Postion:\t" + this.custNameLocation + "\n" +
                        "Task Type Pos:\t" + this.taskTypeLocation + "\n" +
                        "Task Name Pos:\t" + this.taskNameLocation + "\n" +
                        "Task Desc Pos:\t" + this.taskDescriptionLocation + "\n";
        
        return str;
    }
    
}
