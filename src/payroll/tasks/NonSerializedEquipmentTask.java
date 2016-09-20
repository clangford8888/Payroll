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
public class NonSerializedEquipmentTask extends EquipmentTask{
    String itemNumber;
    
    public NonSerializedEquipmentTask(String inName, String inDescription){
        super(inName, inDescription);
        itemNumber = lookupItemNumber(inName); 
    }
    
    @Override
    public String getItemNumber(){
        return itemNumber;
    }
    
    // TODO ******************************* LOOKUP ITEM NUMBER IN TABLE
    public String lookupItemNumber(String inName){
        return "";
    }
    
    @Override
    public String toString(){
        return this.getTaskDescription();
    }
}
