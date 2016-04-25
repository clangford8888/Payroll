/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.tasks;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Casey
 * The TaskReader class will build a list of SHS, Labor, and Equipment tasks
 * so the task creation factories will not have to access the database directly.
 */
public class TaskReader {
    
    private List<Task> nonSerializedList;
    private List<Task> serializedList;
    private List<Task> standardLaborList;
    private List<Task> shsLaborList;
    private TaskReaderDAO taskDAO;
    
    public TaskReader(){
        nonSerializedList = new ArrayList<>();
        serializedList = new ArrayList<>();
        standardLaborList = new ArrayList<>();
        shsLaborList = new ArrayList<>();
        taskDAO = new TaskReaderDAO();
        updateTaskLists();
    }
    
    private void updateTaskLists(){
        nonSerializedList = taskDAO.getNonSerializedTable();
        serializedList = taskDAO.getSerializedTable();
        standardLaborList = taskDAO.getStandardLaborTable();
        shsLaborList = taskDAO.getSHSLaborTable();
    }
    
    public void display(){
        for(Task t : nonSerializedList){
            System.out.println(t.getTaskName() + " " + t.getTaskDescription());
        }
        System.out.println();
        for(Task t : serializedList){
            System.out.println(t.getTaskName() + " " + t.getTaskDescription());
        }
        System.out.println();
        for(Task t : shsLaborList){
            //System.out.println("HELLO");
            System.out.println(t.getTaskName() + " " + t.getTaskDescription());
        }
    }
    
    private static boolean isNonSerializedEquipment(String inTaskName){
        
        
        return true;
    }
    
    private static boolean isSerializedEquipment(String inTaskDescription){
        
        return true;
    }
    
    private static boolean isStandardLaborTask(String inTaskName){
        
        
        return true;
    }
    
    private static boolean isSHSLaborTask(String inTaskName){
        
        
        return true;
    }
    
}
