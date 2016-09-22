/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import payroll.PaymentFileFormatChecker;
import payroll.tasks.NonSerializedEquipmentTask;
import payroll.tasks.SerializedEquipmentTask;
import payroll.tasks.TaskFactory;
import payroll.tasks.TaskCache;

/**
 *
 * @author Casey
 */
public class JobFactory {
    
    private final PaymentFileFormatChecker checker;
    private final TaskFactory taskFactory;
    private final TaskCache masterTaskList;
    private final EquipmentDAO eqDAO;
    
    public JobFactory(PaymentFileFormatChecker inChecker){
        this.checker = inChecker;
        this.masterTaskList = new TaskCache();
        this.taskFactory = new TaskFactory(masterTaskList, checker);
        this.eqDAO = new EquipmentDAO();
    }
    
    // TODO: GETTER/SETTER METHODS FOR inputFile? In case want to use same obj
    //          to parse multiple files.
    

    /*
    Purpose: Traverse a HashMap of <WO#, List<Row>> and build jobs for
            each work order number.
    */
    public void processMap(Map<String, List<HSSFRow>> inMap){
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
            Job createdJob = createJobFromRow(advancedJobType, firstRow);
            // Task factory will create a task from each row
            
            // TODO: Change this to have taskFactory return a task
            // No need to pass in the job.
            for(HSSFRow row : rowList){
                // Task newTask = taskFactory.createTask(row);
                //Task newTask = taskFactory.createTask(row);
                // Job.addTask(newTask);
                
                // OLD WAY HERE **********
                taskFactory.createTask(createdJob, row);
            }

            jobCreatedCount++;
            
            // Calculate Job payment
            int payment = createdJob.calculatePay();
            // Set the payment
            createdJob.setPayment(payment);
            
            // Push to Database
            if(jobDAO.addJob(createdJob)){
                pushJobEquipmentToDatabase(createdJob);
            }
            else{ // Todo: Throw exception?
                System.out.println("Job could not be added.");
                System.out.println(createdJob.getWorkOrderNumber());
            }
            
            //jobDAO.deleteJob(createdJob);
        }
        System.out.println("Number Jobs Created: " + jobCreatedCount);
    }
    
    private void pushJobEquipmentToDatabase(Job newJob){
        eqDAO.setJob(newJob);
        // Add Serialized Equipment to database
        List<SerializedEquipmentTask> list = newJob.getSerializedEquipmentTaskList();
        if(!list.isEmpty()){               
            eqDAO.addSerializedEquipmentFromList(list);
        }
        // Add Non-Serialized Equipment to database
        List<NonSerializedEquipmentTask> nsList = newJob.getNonSerializedEquipmentTaskList();            
        if(!nsList.isEmpty()){
            eqDAO.addNonSerializedEquipmentFromList(nsList);
        }
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
    private Job createJobFromRow(String jobType, HSSFRow inRow){
    
        RowParser rowParser = new RowParser(checker);
        
        rowParser.processRow(inRow);
        String accountNumber = rowParser.getAccountNumber();
        String workOrderNumber = rowParser.getWorkOrderNumber();
        Date date = rowParser.getDate();
        String techID = rowParser.getTechID();
        String customerName = rowParser.getCustomerName();
        
        // Switch on jobType to determine the sublcass & designation of job to build
        String designation;
        Job newJob;
        switch(jobType){
            case "ARA":
            case "SC":
            case "STB":
                designation = "SC";
                newJob = new ServiceCall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, jobType);
                break;
            case "TC":
                designation = "TC";
                newJob = new ServiceCall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, jobType);
                break;
            case "CH":
                designation = "DIU";
                newJob = new ServiceChange(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, jobType);
                break;
            case "HCH":
                designation = "HCH";
                newJob = new ServiceChange(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, jobType);
                break;
            case "DN":
            case "WB":
                designation = "INT";
                newJob = new InternetInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, jobType);
                break;
            case "EHJM":
            case "EHM":
            case "HMV":
                designation = "DM";
                newJob = new HopperInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, jobType);
                break;
            case "HNC":
                designation = "NC";
                newJob = new HopperInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, jobType);
                break;
            case "MV":
                designation = "DM";
                newJob = new StandardInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, jobType);
                break;
            case "NC":
            case "RS":
                designation = "NC";
                newJob = new StandardInstall(accountNumber, workOrderNumber, 
                            date, designation, techID, customerName, jobType);
                break; 
            default:
                System.out.println("JOB TYPE NOT FOUND!");
                return null;
        }
        
        return newJob;
        
    }   
}
