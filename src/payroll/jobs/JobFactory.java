/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import payroll.PaymentFileFormatChecker;

/**
 *
 * @author Casey
 */
public class JobFactory {
    
    private final PaymentFileFormatChecker formatChecker;
    
    public JobFactory(PaymentFileFormatChecker inChecker){
        this.formatChecker = inChecker;
    }
    
    /*
    Purpose: Accepts a row from the HashMap and creates a job from the data.
            The format of the row will have been checked prior to the HashMap
            creation. At this time, sticking with hard coding the location of
            the cells containing relevant information.
    */
    public Job createJob(HSSFRow unprocessedRow){
    
        RowParser rowParser = new RowParser(formatChecker);
        rowParser.processRow(unprocessedRow);
        String accountNumber = rowParser.getAccountNumber();
        String workOrderNumber = rowParser.getWorkOrderNumber();
        Date date = rowParser.getDate();
        String techID = rowParser.getTechID();
        String customerName = rowParser.getCustomerName();
        String designation;
        Job newJob;
        String advancedJobType = getAdvancedJobType(unprocessedRow);
        switch(advancedJobType){
            case "ARA":
            case "SC":
            case "STB":
                designation = "SC";
                newJob = new ServiceCall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, advancedJobType);
                break;
            case "TC":
                designation = "TC";
                newJob = new ServiceCall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, advancedJobType);
                break;
            case "CH":
                designation = "DIU";
                newJob = new ServiceChange(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, advancedJobType);
                break;
            case "HCH":
                designation = "HCH";
                newJob = new ServiceChange(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, advancedJobType);
                break;
            case "DN":
            case "WB":
                designation = "INT";
                newJob = new InternetInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, advancedJobType);
                break;
            case "EHJM":
            case "EHM":
            case "HMV":
                designation = "DM";
                newJob = new HopperInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, advancedJobType);
                break;
            case "HNC":
                designation = "NC";
                newJob = new HopperInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, advancedJobType);
                break;
            case "MV":
                designation = "DM";
                newJob = new StandardInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, advancedJobType);
                break;
            case "NC":
            case "RS":
                designation = "NC";
                newJob = new StandardInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, advancedJobType);
                break; 
            default:
                // TODO throw exception
                System.out.println("JOB TYPE NOT FOUND!");
                return null;
        }
        
        return newJob;
        
    }
    
    /*
    Purpose: Take a HSSFRow and return the job type so the factory can
             build the correct type of job.
    Parameters: inList: List of HSSFRow. 
    */
    private String getAdvancedJobType(HSSFRow unprocessedRow){      
        int advancedJobTypeIndex = formatChecker.getAdvancedTypeLocation();
        HSSFRow currentRow = unprocessedRow;
        HSSFCell advancedTypeCell = currentRow.getCell(advancedJobTypeIndex);
        String jobType = advancedTypeCell.getStringCellValue();
        return jobType;
    }
}
