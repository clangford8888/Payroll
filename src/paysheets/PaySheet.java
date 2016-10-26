/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * A Pay sheet represents one technician's jobs for one day, which he will be
 * paid for. Pay sheets display basic info about the job so the payroll team
 * can audit the amount paid and verify the technician will be paid correctly.
 * 
 * @author Casey
 */
public class PaySheet {
    
    private final String techName;
    private final Date startDate;
    private final Date endDate;
    private HSSFWorkbook workbook;
    private int numJobs;
    private int currentRow;
    
    // 
    private final String paySheetName;
    
    // Indicies for first row of pay sheet entries
    protected static final int DATE_INDEX = 0;
    protected static final int CUST_INDEX = 1;
    protected static final int PAY_INDEX = 2;
    protected static final int NONSERIAL_INDEX = 3;
    protected static final int SERIAL_INDEX = 4;
    protected static final int SHS_INDEX = 5;

    // Second row indicies
    protected static final int WO_INDEX = 0;
    protected static final int TYPE_INDEX = 1;
    protected static final int LEP_INDEX = 2;
    

    public PaySheet(String techName, Date start, Date end){
        this.techName = techName;
        this.startDate = start;
        this.endDate = end;
        this.workbook = new HSSFWorkbook();
        this.numJobs = 0;
        // First two rows are formatting only.
        this.currentRow = 2;
        this.paySheetName = "";
        
        PaySheetFormatter.addTitleRow(workbook);
    }
    
    /*
        Getter methods for PaySheet
    */ 
    public String getTechName(){
        return this.techName;
    }
    
    public Date getStartDate(){
        return this.startDate;
    }
    
    public Date getEndDate(){
        return this.endDate;
    }
    
    /**
     * Adds an entry to the pay sheet.
     * 
     * Each entry will be a job the technician will need to be paid for.
     * @param inEntry  PaySheetEntry to be added to the workbook.
     */
    protected void addEntry(PaySheetEntry inEntry){
        if(inEntry != null){
            // Calculate the index of the new entry
            int rowIndex = getNextRowIndex();
            // Add formatting to the row to be added.
            PaySheetFormatter.addJobFormatting(workbook, rowIndex);

            // Add first row information to the new entry
            addFirstRowData(inEntry, rowIndex);
            // Add second row information to the new entry
            addSecondRowData(inEntry, rowIndex);
            // Add border formatting
            PaySheetFormatter.addJobBorder(workbook, rowIndex);
            // Increment the count of jobs on this pay sheet.
            numJobs++;
        }
    }
    
    private void addFirstRowData(PaySheetEntry inEntry, int rowIndex){
        HSSFSheet sheet = workbook.getSheetAt(0);
        HSSFRow row = sheet.getRow(rowIndex);
        HSSFCell cell;
            
        cell = row.getCell(DATE_INDEX);
        Date date = inEntry.getDate();
        cell.setCellValue(date);
        cell = row.getCell(CUST_INDEX);
        String customer = inEntry.getCustomer();
        cell.setCellValue(customer);
        cell = row.getCell(PAY_INDEX);
        int pay = inEntry.getPay();
        cell.setCellValue(pay); 

        // Add non-Serialized equipment to the non-serialized cell
        addNonSerialListData(inEntry, row, cell);
        // Add serialized equipment to the serialized cell
        addSerializedListData(inEntry, row, cell);
        //Add SHS equipment to the SHS cell
        addSHSData(inEntry, row, cell);
    }
    
    private void addNonSerialListData(PaySheetEntry inEntry, HSSFRow row, HSSFCell cell){
        cell = row.getCell(NONSERIAL_INDEX);
        CellStyle wrapNewLines = workbook.createCellStyle();
        wrapNewLines.setWrapText(true);
        cell.setCellStyle(wrapNewLines);
        List<String> nonSerialList = inEntry.getNonSerializedList();
        if(!nonSerialList.isEmpty()){
            String nonSerialString = "";
            // Using normal for loop to interate so we don't add a new line
            // at the end of the cell
            int listSize = nonSerialList.size();
            for(int i = 0; i < listSize; i++){
                if(i < listSize - 1){
                    // Add a new line on to the end of the cell
                    nonSerialString = nonSerialString + nonSerialList.get(i)
                                        + "\n";
                }
                else{
                    nonSerialString = nonSerialString + nonSerialList.get(i);
                }
            }
            cell.setCellValue(nonSerialString);
        }
    }
    
    private void addSHSData(PaySheetEntry inEntry, HSSFRow row, HSSFCell cell){
        cell = row.getCell(SHS_INDEX);
        CellStyle wrapNewLines = workbook.createCellStyle();
        wrapNewLines.setWrapText(true);
        cell.setCellStyle(wrapNewLines);
        List<String> shsList = inEntry.getSHSList();
        if(!shsList.isEmpty()){
            String shsString = "";
            // Using normal for loop to interate so we don't add a new line
            // at the end of the cell
            int listSize = shsList.size();
            for(int i = 0; i < listSize; i++){
                if(i < listSize - 1){
                    // Add a new line on to the end of the cell
                    shsString = shsString + shsList.get(i)
                                        + "\n";
                }
                else{
                    shsString = shsString + shsList.get(i);
                }
            }
            cell.setCellValue(shsString);
        }
    }
    
    private void addSerializedListData(PaySheetEntry inEntry, HSSFRow row,
                                        HSSFCell cell){
        cell = row.getCell(SERIAL_INDEX);
        // Set cell style to wrap lines
        CellStyle wrapNewLines = workbook.createCellStyle();
        wrapNewLines.setWrapText(true);
        cell.setCellStyle(wrapNewLines);
        List<String> serialList = inEntry.getSerializedList();
        if(!serialList.isEmpty()){
            String serialString = "";
            // Using normal for loop to interate so we don't add a new line
            // at the end of the cell
            int listSize = serialList.size();
            for(int i = 0; i < listSize; i++){
                if(i < listSize-1){
                    serialString = serialString + serialList.get(i) + "\n";
                }
                else{
                    // Don't add newline on last element
                    serialString = serialString + serialList.get(i);
                }
            }
            cell.setCellValue(serialString);
        }
    }
    
    /**
     * TODO: Finish LEP
     * @param inEntry
     * @param rowIndex 
     */
    private void addSecondRowData(PaySheetEntry inEntry, int rowIndex){
        HSSFSheet sheet = workbook.getSheetAt(0);
        // Set the row to the correct position (the row under the first row)
        HSSFRow row = sheet.getRow(rowIndex + 1);
        HSSFCell cell;

        cell = row.getCell(WO_INDEX);
        String workOrderNumber = inEntry.getWorkOrderNumber();
        cell.setCellValue(workOrderNumber);
        cell = row.getCell(TYPE_INDEX);
        String type = inEntry.getType();
        cell.setCellValue(type);
        cell = row.getCell(LEP_INDEX);
        String lep = inEntry.getLEP();
        //cell.setCellValue(lep);

        /*
        TODO: make sure LEP gets added correctly
        */
    }
    
    // Method to determine where a job should be added to a sheet
    private int getNextRowIndex(){
        
        // Each job will take two lines, rows are 0-based and we add 2 lines
        // for the column headers
        int rowIndex = (2 * numJobs) + 2;
        
        return rowIndex;
    }
    
    public HSSFWorkbook getWorkbook(){
        return this.workbook;
    }
}
