/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.gui;

import javax.swing.DefaultListSelectionModel;

/**
 *
 * @author Casey
 */
public class MultipleSelectionModel extends DefaultListSelectionModel{
    
    public MultipleSelectionModel(){
    }
    
    @Override
    public void setSelectionInterval(int index0, int index1){
        if(super.isSelectedIndex(index0)){
            super.removeSelectionInterval(index0, index1);
        }
        else{
            super.addSelectionInterval(index0,index1);
        }
    }
}
