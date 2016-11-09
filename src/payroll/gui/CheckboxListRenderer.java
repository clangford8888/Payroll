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
public class CheckboxListRenderer extends JCheckBox implements
        ListCellRenderer<CheckListItem>{
    
    public CheckboxListRenderer(){
        
    }
    
    @Override
    public Component getListCellRendererComponent(
                                    JList<? extends CheckListItem> list,
                                    CheckListItem value,
                                    int index,
                                    boolean isSelected,
                                    boolean cellHasFocus){
        setEnabled(list.isEnabled());
        setSelected(value.isSelected());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setText(value.toString());
        return this;
    }
}

