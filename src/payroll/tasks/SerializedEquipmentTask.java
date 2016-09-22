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
public class SerializedEquipmentTask implements EquipmentTask{
    
    private String serialNumber;
    private final String model;
    private final String itemNumber;
    private final String taskDescription;
    
    public SerializedEquipmentTask(String inName, String inDescription){
        this.serialNumber = "";
        this.model = inName;
        this.taskDescription = inDescription;
        this.itemNumber = lookupItemNumber(inName);
    }
    
    public SerializedEquipmentTask(String inName, String inDescription, 
                                    String inSerialNumber){
        this.serialNumber = inSerialNumber;
        this.model = inName;
        this.taskDescription = inDescription;
        this.itemNumber = lookupItemNumber(inName);
    }
    
    public void setSerialNumber(String serialNumber){
        this.serialNumber = serialNumber;
    }
    
    @Override
    public String getTaskType(){
        return EquipmentTask.TASK_TYPE;
    }
    
    @Override
    public String getTaskName(){
        return this.model;
    }
    
    @Override
    public String getTaskDescription(){
        return this.taskDescription;
    }
    
    @Override
    // Getter method for Item Number
    public String getItemNumber(){
        return itemNumber;
    }
    
    // TODO: LOOKUP ITEM NUMBER FROM NAME!! ********************************
    private String lookupItemNumber(String inName){
        return "ITEM NUMBER";
    }
    
    public String getSerialNumber(){
        return serialNumber;
    }
    
    // **************************************************** may remove??
    // More descriptive name for receiver/modem/tria type
    public String getModel(){
        return this.model;
    }
    
    @Override
    public String toString(){
        return this.model + " " + this.getSerialNumber();
    }
    
}
