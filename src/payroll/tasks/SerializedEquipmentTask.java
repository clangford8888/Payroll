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
public class SerializedEquipmentTask extends EquipmentTask{
    
    private final String serialNumber;
    private final String model;
    private String itemNumber;
    
    public SerializedEquipmentTask(String inName, String inDescription){
        super(inName, inDescription);
        
        serialNumber = inName;
        model = inDescription;
        itemNumber = lookupItemNumber(inName);
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
    // otherwise could use getTaskDescription from super method
    public String getModel(){
        return this.model;
    }
    
}
