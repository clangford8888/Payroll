/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import payroll.jobs.Job;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import payroll.tasks.NonSerializedEquipmentTask;
import payroll.PaymentFileFormatChecker;
import payroll.tasks.SerializedEquipmentTask;
import payroll.tasks.TaskBuilder;

/**
 *
 * @author Casey
 */
public class JobBuilder {
    
    private final PaymentFileFormatChecker checker;
    
    public JobBuilder(PaymentFileFormatChecker inChecker){
        this.checker = inChecker;
    }
    
    // TODO: GETTER/SETTER METHODS FOR inputFile? In case want to use same obj
    //          to parse multiple files.
    

    /*
    Purpose: Traverse a HashMap of <WO#, List<Row>> and build jobs for
            each work order number.
    */
    public void buildJobsFromMap(Map<String, List<HSSFRow>> inMap){
        // Initialize Map of WorkOrderNumber/List of Rows 
        Map<String, List<HSSFRow>> map = inMap;
        // Create a new list of rows
        List<HSSFRow> rowList;
        
        int jobCreatedCount = 0;
        JobDAO jobDAO = new JobDAO();
        
        // For each work order, get List of rows containing task data
        for(String wo : map.keySet()){
            // Get row list for each work order number and create a new job
            rowList = map.get(wo);            
            // Get first row from list to determine type and create job
            HSSFRow firstRow = rowList.get(0);
            // Find the job type
            String advancedJobType = getJobType(firstRow);        
            // Create appropriate job 
            Job createdJob = createJob(advancedJobType, firstRow);
            
            // Traverse list of rows and add tasks to job's Task list
            for(HSSFRow row : rowList){
                // Create a task for each row. TaskBuilder will add task to job
                TaskBuilder.createTask(createdJob, row, checker);
                
            }
            jobCreatedCount++;
            
            // Calculate Job payment
            int payment = createdJob.calculatePay();
            // Set the payment
            createdJob.setPayment(payment);
            
            // Push to Database
            
            // *** DEBUG
            //JobDAO jobDAO = new JobDAO();
            //jobDAO.addJob(createdJob);
            
            //jobDAO.deleteJob(createdJob);
            
            List<SerializedEquipmentTask> list = createdJob.getSerializedEquipmentTaskList();
            
            if(!list.isEmpty()){
                EquipmentDAO eqDAO = new EquipmentDAO(createdJob);
                
                //eqDAO.addSerializedEquipmentFromList(list);
            }
            
            List<NonSerializedEquipmentTask> nsList = createdJob.getNonSerializedEquipmentTaskList();
            
            if(!nsList.isEmpty()){
                EquipmentDAO eqDAO = new EquipmentDAO(createdJob);
                //eqDAO.addNonSerializedEquipmentFromList(nsList);
            }
            
            
            //jobDAO.deleteJob(createdJob);
            
        }
        System.out.println("Number Jobs Created: " + jobCreatedCount);
    }
    
    /*
    Purpose: Take in a list of Rows and return the job type so the class can
             build the correct type of job.
    Parameters: inList: List of HSSFRow. 
    */
    public String getJobType(HSSFRow inRow){
        
        int advancedJobTypeIndex = checker.getAdvancedTypeLocation();
        
        HSSFRow currentRow = inRow;
        // Get cell located in the Advanced Type location
        HSSFCell advancedTypeCell = currentRow.getCell(advancedJobTypeIndex);
        // Get String value of Advanced Type Cell and assign to job type
        String jobType = advancedTypeCell.getStringCellValue();
        // Return job type
        return jobType;
    }
    
    /*
    Purpose: Accepts a row from the HashMap and creates a job from the data.
            The format of the row will have been checked prior to the HashMap
            creation. At this time, sticking with hard coding the location of
            the cells containing relevant information.
    */
    private Job createJob(String inJobType, HSSFRow inRow){
        String jobType = inJobType;
        HSSFRow currentRow = inRow;
        
        int accountNumberIndex = checker.getAccountNumLocation();
        int workOrderIndex = checker.getWorkOrderLocation();
        int dateIndex = checker.getDateLocation();
        int techIDIndex = checker.getTechIDLocation();
        int customerNameIndex = checker.getCustNameLocation();
        
        // May separate out into different method for readability ****
        // Get all relevant job information (WO# ACC# DATE, ETC)
        HSSFCell accountNumberCell = currentRow.getCell(accountNumberIndex);
        String accountNumber = accountNumberCell.getStringCellValue();      
        HSSFCell workOrderNumberCell = currentRow.getCell(workOrderIndex);
        String workOrderNumber = workOrderNumberCell.getStringCellValue();
        HSSFCell dateCell = currentRow.getCell(dateIndex);
        Date date = dateCell.getDateCellValue();
        HSSFCell techIDCell = currentRow.getCell(techIDIndex);
        String techID = techIDCell.getStringCellValue();
        HSSFCell customerNameCell = currentRow.getCell(customerNameIndex);
        String customerName = customerNameCell.getStringCellValue();
        
        // Switch on jobType to determine the sublcass & designation of job to build
        String designation;
        Job newJob;
        switch(jobType){
            case "ARA":
            case "SC":
            case "STB":
                designation = "SC";
                newJob = new ServiceCall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName);
                break;
            case "TC":
                designation = "TC";
                newJob = new ServiceCall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName);
                break;
            case "CH":
                designation = "DIU";
                newJob = new ServiceChange(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName);
                break;
            case "HCH":
                designation = "HCH";
                newJob = new ServiceChange(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName);
                break;
            case "DN":
            case "WB":
                designation = "INT";
                newJob = new InternetInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName);
                break;
            case "EHJM":
            case "EHM":
            case "HMV":
                designation = "DM";
                newJob = new HopperInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName);
                break;
            case "HNC":
                designation = "NC";
                newJob = new HopperInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName);
                break;
            case "MV":
                designation = "DM";
                newJob = new StandardInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName);
                break;
            case "NC":
            case "RS":
                designation = "NC";
                newJob = new StandardInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName);
                break; 
            default:
                System.out.println("JOB TYPE NOT FOUND!");
                return null;
        }
        
        return newJob;
        
    }   
}
