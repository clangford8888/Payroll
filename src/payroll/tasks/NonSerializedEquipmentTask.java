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
public class NonSerializedEquipmentTask implements EquipmentTask{
    
    private final String taskName;
    private final String itemNumber;
    private final String taskDescription;
    
    public NonSerializedEquipmentTask(String inName, String inDescription){
        this.taskName = inName;
        this.taskDescription = inDescription;
        this.itemNumber = lookupItemNumber(inName);
    }
    
    @Override
    public String getItemNumber(){
        return itemNumber;
    }
    
    @Override
    public String getTaskType(){
        return EquipmentTask.TASK_TYPE;
    }
    
    @Override
    public String getTaskName(){
        return this.taskName;
    }
    
    @Override
    public String getTaskDescription(){
        return this.taskDescription;
    }
    
    // TODO ******************************* LOOKUP ITEM NUMBER IN TABLE
    public String lookupItemNumber(String inName){
        return "";
    }
    
    @Override
    public String toString(){
        return this.taskDescription;
    }
}
