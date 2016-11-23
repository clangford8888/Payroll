/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.gui;

import javax.swing.JCheckBox;

/**
 *
 * @author Casey
 */
public class TechCheckListItem extends JCheckBox{
    private final String techID;
    private final String firstName;
    private final String lastName;
    
    // Initialize all items as checked.
    public TechCheckListItem(String id, String fName, String lName){
        this.techID = id;
        this.firstName = fName;
        this.lastName = lName;
        this.setSelected(true);
    }
    
    @Override
    public String toString(){
        return firstName + " " + lastName;
    }

    public String getTechID(){
        return this.techID;
    }
}
