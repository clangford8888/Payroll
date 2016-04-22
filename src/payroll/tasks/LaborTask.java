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
public abstract class LaborTask extends Task{
    
    private String taskType;
    private int payment;
    
    public LaborTask(String inName, String inDescription){
        super(inName,inDescription);
        this.taskType = "Labor";
        this.payment = 0;
    }
    
    @Override
    public String getTaskType(){
        return taskType;
    }
    
    public int getPayment(){
        return payment;
    }
    
    public void setTaskType(String inType){
        taskType = inType;
    }
    
    public void setPayment(int inPay){
        this.payment = inPay;
    }
    
    // TODO: Get rid of this!
    
    public abstract String getAdvancedTaskType();
    
    public abstract int lookupLaborPayment();
}
