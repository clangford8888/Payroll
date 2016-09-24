/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import java.util.Date;
import java.util.List;
import payroll.tasks.LaborTask;
import payroll.tasks.NonSerializedEquipmentTask;
import payroll.tasks.SHSLaborTask;
import payroll.tasks.SerializedEquipmentTask;
import payroll.tasks.StandardLaborTask;
import payroll.tasks.Task;

/**
 *
 * @author Casey
 */
public class ServiceChange extends Job{
    
    public ServiceChange(String inANum, String inWONum, Date inDate, 
            String inDesignation, String inTID, String inCName, String inJobType){
        super(inANum, inWONum, inDate, inDesignation, inTID, inCName, inJobType);
    }
    
    
    /**
     * 
     * @return 
     */
    @Override
    public int calculatePay(){
        // Initial pay is set to 0
        int pay = 0;
        String designation = this.getDesignation();
        // Get the standard labor list for this object
        List<StandardLaborTask> laborList = this.getStandardLaborTaskList();
        // If the labor list contains an internet install task
        if(containsInternetInstall(laborList)){
            // Calculate the internet install pay
            pay = calculateInternetInstallPay();
            return pay;
        }
        // Else check if job is a hopper upgrade
        else if(designation.equals("HCH")){
            
            pay = calculateHopperDIUPay(laborList);

            // Add additional SHS pay
            pay += this.calculateSHSPay();
            return pay;
        }
        
        // Get total number of labor tasks.
        int standardCount = laborList.size();
        
        if(standardCount >= 1){
            pay = 40;
            // Pay $20 for additional labor tasks after the first
            pay += (standardCount - 1) * 20;
        }
        
        // Add additional shs pay
        pay += this.calculateSHSPay();
        
        return pay;
    }
    
    
    // TODO: ************ NEED TO TEST THIS METHOD ***************************
    private static boolean containsInternetInstall(
                                    List<StandardLaborTask> inList){
        StandardLaborTask internetTask = new StandardLaborTask(
                                            "-K", "Set Up Broadband by Sat");
        // Check if labor list contains internet install task
        if(inList.contains(internetTask)){
            return true;
        }
        return false;
    }
    
    /**
     * Method to calculate the pay for an Internet install. This method will
     * build an InternetInstall job from the calling job's fields. The pay for
     * the Internet install is then returned.
     * 
     * @return pay: representation of pay for the job.
     */
    private int calculateInternetInstallPay(){
        int pay;
        List<StandardLaborTask> laborList = this.getStandardLaborTaskList();
        // Create an Internet Install Job
        InternetInstall internetJob = new InternetInstall(
                                                this.getAccountNumber(),
                                                this.getWorkOrderNumber(),
                                                this.getDate(),
                                                this.getDesignation(),
                                                this.getTechID(),
                                                this.getCustomerName(),
                                                this.getJobType());
        // Traverse standard labor list and add tasks to new job
        for(StandardLaborTask task : laborList){
            internetJob.addLaborTask(task);
        }
        // Traverse shs task list and add tasks to new job
        for(SHSLaborTask task : this.getSHSTaskList()){
            internetJob.addLaborTask(task);
        }
        // Traverse equipment task list and add tasks to new job
        for(NonSerializedEquipmentTask task: this.getNonSerializedEquipmentTaskList()){
            internetJob.addEquipmentTask(task);
        }
        for(SerializedEquipmentTask task: this.getSerializedEquipmentTaskList()){
            internetJob.addEquipmentTask(task);
        }
        // Use existing internet install's method to calculate pay
        pay = internetJob.calculatePay();
        // Return calculated pay
        return pay;
    }
    
    /**
     * Method to display all labor tasks. Used for debugging.
     * @param list ArrayList<StandardLaborTask> list of tasks to display
     */
    private void displayLaborTasks(List<StandardLaborTask> list){
        for(Task task : list){
            System.out.println(task.getTaskDescription());
        }
    }
    
    /**
     * 
     * @param list
     * @return 
     */
    private int calculateHopperDIUPay(List<StandardLaborTask> list){
        // Base pay for Hopper DIU is 40
        int pay = 40;
        // Initialize variables to count # of receivers installed
        int hopperCount = 0;
        int joeyCount = 0;
        // Traverse list of tasks
        for(LaborTask task : list){
            // Get individiual task's name
            String taskName = task.getTaskName();
            // Count number of each type of receiver installed
            if(taskName.equals("FC")){
                hopperCount++;
            }
            else if(taskName.equals("FD")  ||
                    taskName.equals("FA2") ||
                    taskName.equals("EB6")){
                joeyCount++;
            }
            else if(!taskName.equals("?=")){
                pay += task.lookupLaborPayment();
            }
        }
        // Add $20 for each additional hopper after the first
        if(hopperCount>1){
            pay += (hopperCount - 1) * 20;
        }
        /*
            First joey is either included with first hopper, or it is included
            in the base price of 40 when there are no hoppers being installed.
        */
        if(joeyCount>1){
            pay += (joeyCount - 1) * 15;
        }
        return pay;
    }
}
