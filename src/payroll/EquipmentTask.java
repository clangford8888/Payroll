/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

/**
 *
 * @author Casey
 */
public abstract class EquipmentTask extends Task {
    String taskType;
    
    public EquipmentTask(String inName, String inDescription){
        
        super(inName,inDescription);
        this.taskType = "Equipment";
    }
    
    public String getTaskType(){
        return taskType;
    }
    
    public abstract String getItemNumber();
    
}
