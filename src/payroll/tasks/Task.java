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
public abstract class Task {

    private String taskName;
    private String taskDescription;
    
    public Task(String inTaskName, String inTaskDescription){
        
        this.taskName = inTaskName;
        this.taskDescription = inTaskDescription;

    }
    
    // Abstract method implemented by various types of tasks to return task type
    public abstract String getTaskType();
    
    public String getTaskName(){
        return this.taskName;
    }
    
    public String getTaskDescription(){
        return this.taskDescription;
    }
    
}
