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
public interface EquipmentTask extends Task {
    
    static final String TASK_TYPE = "Equipment";
    
    @Override
    public String getTaskType();
    
    public String getItemNumber();
    
}
