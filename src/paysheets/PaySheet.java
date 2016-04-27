/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import payroll.jobs.Job;

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
    
    public PaySheet(String techName, Date start, Date end){
        this.techName = techName;
        this.startDate = start;
        this.endDate = end;
        this.workbook = new HSSFWorkbook();
        this.numJobs = 0;
        
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
    
    // Method to add Jobs to pay sheet
    public void addJob(Job inJob){
        if(inJob != null){
            
            int rowIndex = getNextRowIndex();
            
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
