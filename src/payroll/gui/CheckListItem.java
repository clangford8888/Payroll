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
public class CheckListItem extends JCheckBox{
    private final String firstName;
    private final String lastName;
    
    // Initialize all items as cheched.
    public CheckListItem(String fName, String lName){
        firstName = fName;
        lastName = lName;
        this.setSelected(true);
    }
    
    @Override
    public String toString(){
        return firstName + " " + lastName;
    }
}
