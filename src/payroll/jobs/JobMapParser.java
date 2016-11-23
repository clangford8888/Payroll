/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import payroll.PaymentFileFormatChecker;
import payroll.tasks.NonSerializedEquipmentTask;
import payroll.tasks.SHSLaborTask;
import payroll.tasks.SerializedEquipmentTask;
import payroll.tasks.StandardLaborTask;
import payroll.tasks.Task;
import payroll.tasks.TaskFactory;

/**
 *
 * @author Casey
 */
public class JobMapParser {
    
    private final JobFactory jobFactory;
    private final PaymentFileFormatChecker formatChecker;
    private int createdJobCount;
    
    private static final int FIRST_ROW_INDEX = 0;
    
    private final Map<String, List<HSSFRow>> jobMap;
    
    public JobMapParser(Map<String, List<HSSFRow>> inMap, PaymentFileFormatChecker inFormatChecker){
        this.jobMap = inMap;
        this.formatChecker = inFormatChecker;
        this.jobFactory = new JobFactory(formatChecker);
        this.createdJobCount = 0;
    }
    
    public int getCreatedJobCount(){
        return this.createdJobCount;
    }
    
    public void parseUnprocessedRowMap(){
        List<HSSFRow> unprocessedRowsList;
        JobDAO jobDAO = new JobDAO();
        for(String workOrderNumber : jobMap.keySet()){
            unprocessedRowsList = jobMap.get(workOrderNumber);            
            HSSFRow firstRow = unprocessedRowsList.get(FIRST_ROW_INDEX);
            Job createdJob = jobFactory.createJob(firstRow);
            processRowList(unprocessedRowsList, createdJob);
            calculateJobPay(createdJob);
            pushJobToDatabase(jobDAO, createdJob);
            createdJobCount++;
        }
        
    }

    private void pushJobToDatabase(JobDAO jobDAO, Job createdJob) {
        if(jobDAO.addJob(createdJob)){
            pushJobEquipmentToDatabase(createdJob);
        }
        else{
            // TODO: Exception Handling Here
            System.out.println("Job could not be added.");
            System.out.println(createdJob.getWorkOrderNumber());
        }
    }

    /**
     * Traverses a List of HSSFRows, creates appropriate Tasks using a TaskFactory
     * and adds them to the Job object.
     * @param rowList
     * @param createdJob 
     */
    private void processRowList(List<HSSFRow> unprocessedRowsList, Job createdJob) {
        TaskFactory taskFactory = new TaskFactory(formatChecker);
        for(HSSFRow row : unprocessedRowsList){
            Task newTask = taskFactory.createTask(row);
            if(newTask instanceof SerializedEquipmentTask){
                createdJob.addEquipmentTask((SerializedEquipmentTask)newTask);
            }
            else if(newTask instanceof NonSerializedEquipmentTask){
                createdJob.addEquipmentTask((NonSerializedEquipmentTask)newTask);
            }
            else if(newTask instanceof StandardLaborTask){
                createdJob.addLaborTask((StandardLaborTask)newTask);
            }
            else if(newTask instanceof SHSLaborTask){
                createdJob.addLaborTask((SHSLaborTask)newTask);
            }
        }
    }
    
    private void calculateJobPay(Job createdJob) {
        int jobPayment = createdJob.calculatePay();
        createdJob.setPayment(jobPayment);
    }
    
    private void pushJobEquipmentToDatabase(Job newJob){
        EquipmentDAO eqDAO = new EquipmentDAO(newJob);
        Set<SerializedEquipmentTask> serializedSet = newJob.getSerializedEquipmentTaskList();
        if(!serializedSet.isEmpty()){               
            eqDAO.addSerializedEquipmentFromSet(serializedSet);
        }
        List<NonSerializedEquipmentTask> nsList = newJob.getNonSerializedEquipmentTaskList();            
        if(!nsList.isEmpty()){
            eqDAO.addNonSerializedEquipmentFromList(nsList);
        }
    }
    
}
