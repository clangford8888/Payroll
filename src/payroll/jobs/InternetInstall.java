/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import payroll.jobs.Job;
import java.util.Date;
import java.util.List;
import payroll.StandardLaborTask;

/**
 *
 * @author Casey
 */
public class InternetInstall extends Job{
    
    public InternetInstall(String inANum, String inWONum, Date inDate, 
            String inDesignation, String inTID, String inCName){
        super(inANum, inWONum, inDate, inDesignation, inTID, inCName);
    
    }
    
    @Override
    public int calculatePay(){
        
        int pay = 0;
        // Get the labor and shs task lists
        List<StandardLaborTask> standardLaborList = this.getStandardLaborTaskList();
        
        // Check to make sure labor list is not null
        if(standardLaborList != null){
            // Internet installs pay 85 by default
            pay = 85;
            // Traverse labor task list
            for(StandardLaborTask task : standardLaborList){
                // Get the task name for each task
                String taskName = task.getTaskName();
                // Check to see if task should be ignored
                if(!isIgnoredTask(taskName)){
                    // Get task's payment value and add it to pay
                    pay += task.lookupLaborPayment();
                }
            }
        }
        else{
            System.out.println("Labor list was null! WO#: " + this.getWorkOrderNumber());
        }
        
        pay += this.calculateSHSPay();
        
        // Return payment
        return pay;
    }
    
    // Check to see if the task needs to be ignored. TVE and Setup BB are ignored
    private static boolean isIgnoredTask(String inTaskName){
        
        if(inTaskName.equals("P2")||inTaskName.equals("-K")){
            return true;
        }
        return false;
    }
   
}
