/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.tasks;

/**
 *
 * @author Casey
 */
public interface LaborTask extends Task{
    
    static final String TASK_TYPE = "Labor";
        
    @Override
    public String getTaskType();
    
    public int getPayment();
    
    public void setPayment(int inPay);
    
    // TODO: Get rid of this!?
    
    public String getAdvancedTaskType();
    
    public int lookupLaborPayment();
}
