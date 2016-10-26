/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import payroll.jobs.Job;
import java.util.Date;
import java.util.List;
import payroll.tasks.LaborTask;
import payroll.tasks.StandardLaborTask;

/**
 *
 * @author Casey
 */
public class HopperInstall extends Job{
    
    
    public HopperInstall(String inANum, String inWONum, Date inDate, String inDesignation,
                String inTID, String inCName, String inJobType){
        super(inANum, inWONum, inDate, inDesignation, inTID, inCName, inJobType);
        
    }
    
    @ Override
    public int calculatePay(){
        // Base pay for Hopper install with only 1 hopper is 70
        int pay = 70;
        // Initialize count of Hoppers
        int hopperCount = 0;
        // Initialize count of Joeys
        int joeyCount = 0;

        List<StandardLaborTask> standardLaborList = this.getStandardLaborTaskList();
        
        if(standardLaborList != null){
            for(StandardLaborTask task : standardLaborList){
                // Check to see if task is a Joey Install
                if(laborTaskIsJoey(task)){
                    // Increase the joey count
                    joeyCount++;
                }
                // Cehck to see if task is a Hopper Install
                else if(laborTaskIsHopper(task)){
                    // Increase the hopper count
                    hopperCount++;
                }
                // Check to see if the task should be ignored for this type of job
                else if(!isIgnoredTask(task)){
                    pay += task.lookupLaborPayment();
                }
            }
        }
        else{
            System.out.println("Empty Labor Task List!" + this.getWorkOrderNumber());
        }
        
        // Add SHS pay
        pay += this.calculateSHSPay();
        
        // First Hopper and Joey pays 75
        // Additional Joeys after the first are worth $15 additional
        if(joeyCount >= 1){
            pay += 5 + (joeyCount - 1)*15;
        }
        // Additional Hoppers after the first are worth $20 additional
        if(hopperCount > 1){
            pay += (hopperCount - 1)*20;
        }
        
        return pay;
    }
    
    /* 
    Purpose: check a labor task to see if it is a Joey.
    */
    private static boolean laborTaskIsJoey(LaborTask inTask){
        String task = inTask.getTaskName();
        // FD -> Joey, FA2 -> Super Joey, EB6 -> Wireless Joey
        if(task.equals("FD")||task.equals("FA2")||task.equals("EB6")){
            return true;
        }
        return false;
    }
    /* 
    Purpose: check a labor task to see if it is a Hopper.
    */
    private static boolean laborTaskIsHopper(LaborTask inTask){
        String task = inTask.getTaskName();
        // Currently only FC showing as Hopper
        if(task.equals("FC")){
            return true;
        }
        return false;
    }
    /*
    Purpose: check a labor task to see if it needs to be ignored for this
        job type.
    */
    private static boolean isIgnoredTask(LaborTask inTask){
        String task = inTask.getTaskName();
        if(task.equals(";K")||task.equals("?=")){
            return true;
        }
        return false;
    }
    
}
