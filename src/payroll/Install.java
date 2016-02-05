/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.util.Date;

/**
 *
 * @author Casey
 */
public abstract class Install extends Job {
    
    public Install(String inANum, String inWONum, Date inDate, String inDesignation,
                String inTID, String inCName){
        super(inANum, inWONum, inDate, inDesignation, inTID, inCName);
        
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public abstract int calculatePay();
    
}
