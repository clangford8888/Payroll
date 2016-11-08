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
    
    public CheckListItem(String fName, String lName){
        firstName = fName;
        lastName = lName;
    }
    
    @Override
    public String toString(){
        return firstName + " " + lastName;
    }
}
