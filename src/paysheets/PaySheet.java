/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
    protected static final int NONSERIAL_INDEX = 2;
    protected static final int SHS_INDEX = 3;
    protected static final int PAY_INDEX = 4;
    protected static final int LEP_INDEX = 5;
    
    // Second row
    protected static final int WO_INDEX = 0;
    protected static final int TYPE_INDEX = 1;
    protected static final int SERIAL_INDEX = 2;

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
     * @param inEntry  entry to be added.
     */
    public void addEntry(PaySheetEntry inEntry){
        if(inEntry != null){
            
            int rowIndex = getNextRowIndex();
            
            Date date = inEntry.getDate();
            String workOrderNumber = inEntry.getWorkOrderNumber();
            String customer = inEntry.getCustomer();
            String type = inEntry.getType();
            List<String> nonSerialList = inEntry.getNonSerializedList();
            List<String> serialList = inEntry.getSerializedList();
            List<String> shsList = inEntry.getSHSList();
            int pay = inEntry.getPay();
            String lep = inEntry.getLEP();

            PaySheetFormatter.addJobFormatting(workbook, rowIndex);
            
            // Increment the count of jobs
            numJobs++;
        }
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
