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
public class SerializedEquipmentTask extends EquipmentTask{
    
    String serialNumber;
    String serializedBoxType;
    String itemNumber;
    
    public SerializedEquipmentTask(String inName, String inDescription){
        super(inName, inDescription);
        
        serialNumber = inName;
        serializedBoxType = inDescription;
        itemNumber = lookupItemNumber(inName);
    }
    
    
    // Getter method for Item Number
    public String getItemNumber(){
        return itemNumber;
    }
    
    // TODO: LOOKUP ITEM NUMBER FROM NAME!! ********************************
    public String lookupItemNumber(String inName){
        return "ITEM NUMBER";
    }
    
    public String getSerialNumber(){
        return serialNumber;
    }
    
    
    // **************************************************** may remove??
    // More descriptive name for receiver/modem/tria type
    // otherwise could use getTaskDescription from super method
    public String getSerializedBoxType(){
        return serializedBoxType;
    }
    
}
