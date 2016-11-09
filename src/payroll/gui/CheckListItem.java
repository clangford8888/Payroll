/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.gui;

/**
 *
 * @author Casey
 */
public class CheckListItem {
    private final String firstName;
    private final String lastName;
    private boolean checked;
    
    public CheckListItem(String fName, String lName){
        firstName = fName;
        lastName = lName;
        checked = true;
    }
    
    @Override
    public String toString(){
        return firstName + " " + lastName;
    }
    
    public boolean isSelected(){
        return checked;
    }
    
    /**
     * This method changes the state of the checked item. If the item is not
     * checked, then it will be checked after this method is called.
     */
    public void switchSelectedState(){
        checked = !checked;
    }
    
    public void setSelectedState(boolean inChecked){
        checked = inChecked;
    }
}
