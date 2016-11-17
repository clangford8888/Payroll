/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.gui;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Casey
 */
public class CheckboxListRenderer implements ListCellRenderer<CheckListItem>{
    
    public CheckboxListRenderer(){
        
    }
    
    @Override
    public Component getListCellRendererComponent(
                                    JList<? extends CheckListItem> list,
                                    CheckListItem value,
                                    int index,
                                    boolean isSelected,
                                    boolean cellHasFocus){
        value.setEnabled(list.isEnabled());
        value.setSelected(value.isSelected());
        value.setFont(list.getFont());
        value.setBackground(list.getBackground());
        value.setForeground(list.getForeground());
        value.setText(value.toString());
        return value;
    }
}

