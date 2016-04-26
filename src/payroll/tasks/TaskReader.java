/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.tasks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Casey
 * The TaskReader class will build a list of SHS, Labor, and Equipment tasks
 * so the task creation factories will not have to access the database directly.
 */
public class TaskReader {
    
    private Map<String,Task> nonSerializedMap;
    private Map<String,Task> serializedMap;
    private Map<String,Task> standardLaborMap;
    private Map<String,Task> shsLaborMap;
    private TaskReaderDAO taskDAO;
    
    public TaskReader(){
        nonSerializedMap = new HashMap<>();
        serializedMap = new HashMap<>();
        standardLaborMap = new HashMap<>();
        shsLaborMap = new HashMap<>();
        taskDAO = new TaskReaderDAO();
        updateTaskLists();
    }
    
    private void updateTaskLists(){
        nonSerializedMap = taskDAO.getNonSerializedMap();
        serializedMap = taskDAO.getSerializedMap();
        standardLaborMap = taskDAO.getStandardLaborMap();
        shsLaborMap = taskDAO.getSHSLaborMap();
    }
    
    public void display(){
        for(String s : nonSerializedMap.keySet()){
            Task t = nonSerializedMap.get(s);
            System.out.println(t.getTaskName() + " " + t.getTaskDescription());
        }
        System.out.println();
        for(String s : serializedMap.keySet()){
            Task t = serializedMap.get(s);
            System.out.println(t.getTaskName() + " " + t.getTaskDescription());
        }
        System.out.println();
        for(String s : standardLaborMap.keySet()){
            Task t = standardLaborMap.get(s);
            System.out.println(t.getTaskName() + " " + t.getTaskDescription());
        }
        System.out.println();
        for(String s : shsLaborMap.keySet()){
            Task t = shsLaborMap.get(s);
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
