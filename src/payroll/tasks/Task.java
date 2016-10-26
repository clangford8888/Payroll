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
public interface Task {
    
    // Abstract method implemented by various types of tasks to return task type
    public String getTaskType();
    
    public String getTaskName();
    
    public String getTaskDescription();
    
}
