/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import java.util.Date;
import java.util.List;
import payroll.tasks.StandardLaborTask;

/**
 *
 * @author Casey
 */
public class ServiceCall extends Job{
    
    public ServiceCall(String inANum, String inWONum, Date inDate, 
            String inDesignation, String inTID, String inCName, String inJobType){
        super(inANum, inWONum, inDate, inDesignation, inTID, inCName, inJobType);
    }
    
    //Base pay passed
    // Other tech warranty passed (before full method completed)
    // Own tech warranty passed (before full method completed)
    
    @Override
    public int calculatePay(){
        
        int pay = 0;
        
        List<StandardLaborTask> standardLaborList = 
                                            this.getStandardLaborTaskList();
        
        // Check if the job is still under warranty
        
        ServiceCallDAO scDAO = new ServiceCallDAO(this);
        
        if(scDAO.checkForWarranty()){
            // If under warranty, is it the tech's own warranty
            if(scDAO.isCurrentTechWarranty()){
                // Only add any additional SHS labor to pay of 0
                pay += this.calculateSHSPay();
                
                return pay;
            }
            // Add chargeback to database for tech under warranty
            scDAO.addChargeback();
        }
        // Set initial pay to 40
        pay = 40;
        // Traverse task list
        for(StandardLaborTask task : standardLaborList){
            // Add task value to payment
            pay += task.lookupLaborPayment();
        }
        
        pay += this.calculateSHSPay();
        
        // Return Service Call Payment Amount
        return pay;
    }
}
