/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import payroll.jobs.Job;
import java.util.Date;
import java.util.List;
import payroll.tasks.StandardLaborTask;

/**
 *
 * @author Casey
 */
public class StandardInstall extends Job{
    
    public StandardInstall(String inANum, String inWONum, Date inDate, 
            String inDesignation, String inTID, String inCName){
        super(inANum, inWONum, inDate, inDesignation, inTID, inCName);
    
    }
    
    @Override 
    public int calculatePay(){
        List<StandardLaborTask> standardLaborList = this.getStandardLaborTaskList();
        // Base pay for Standard Install is 70 for one receiver
        int pay = 70;
        // Initialize receiver count
        int receiverCount = 0;
        // Traverse the task list
        // TODO: *********************** handle if labor list is null
        if(standardLaborList != null){
            for(StandardLaborTask task: standardLaborList){
                String taskName = task.getTaskName();
                // If task is a set up receiver, increment receiver count
                if(taskName.equals("?=")){
                    receiverCount++;
                }
                // Else if labor task is not Set Up Dish
                else if(!taskName.equals(";K")){
                    // Add the additional payment amount(if any) to the payment
                    pay += task.lookupLaborPayment();
                }
            }
        }
        
        pay += this.calculateSHSPay();
        
        // Additional receivers after the first pay an extra $20
        pay += (receiverCount - 1)*20;
        // System.out.print("Receiver Count " + receiverCount);
        return pay;
    }
    
    
}
