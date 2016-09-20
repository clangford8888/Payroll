/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.tasks;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: COMMENT!!
 * @author Casey
 * The TaskReader class will build a list of SHS, Labor, and Equipment tasks
 * so the task creation factories will not have to access the database directly.
 */
public class TaskCache {
    
    private Map<String,? extends EquipmentTask> nonSerializedMap;
    private Map<String, String> serializedMap;
    private Map<String,? extends LaborTask> standardLaborMap;
    private Map<String,? extends LaborTask> shsLaborMap;
    private TaskCacheDAO taskDAO;
    
    public TaskCache(){
        nonSerializedMap = new HashMap<>();
        serializedMap = new HashMap<>();
        standardLaborMap = new HashMap<>();
        shsLaborMap = new HashMap<>();
        taskDAO = new TaskCacheDAO();
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
            //Task t = serializedMap.get(s);
            //System.out.println(t.getTaskName() + " " + t.getTaskDescription());
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
    
    
    
    public EquipmentTask getEquipmentTask(String taskName, String taskDescription){
        // Check if the task name is in the non-serialized map
        if(nonSerializedMap.containsKey(taskName)){
            return nonSerializedMap.get(taskName);
        }
        // Else check if the task is in the serialized map
        else if(serializedMap.containsKey(taskDescription)){
            SerializedEquipmentTask sTask = new SerializedEquipmentTask(taskName, taskDescription);
            sTask.setSerialNumber(taskName);
            return sTask;
        }
        // If the task was not found, return null. Will have to check for null
        // in calling method
        return null;
    }
    
    public LaborTask getLaborTask(String taskName){
        LaborTask newTask;
        if(standardLaborMap.containsKey(taskName)){
            return standardLaborMap.get(taskName);
        }
        else if(shsLaborMap.containsKey(taskName)){
            return shsLaborMap.get(taskName);
        }
        return null;
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