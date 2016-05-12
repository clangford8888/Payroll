/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import payroll.DatabaseConnector;



/**
 *
 * @author Casey
 */
public class TaskReaderDAO {
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public TaskReaderDAO(){
        conn = null;
        ps = null;
        rs = null;
    }
    
    /*
        Changed the approach to use a Map<String,Task> in order to be able to
        quickly search based on the task name, and instantly get the Task
        associated with that task name.
    */
    
    public Map<String,NonSerializedEquipmentTask> getNonSerializedMap(){
        
        Map<String,NonSerializedEquipmentTask> nonSerialized = new HashMap<>();
        
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select * from nonserialized_equipment";
            
            ps = conn.prepareStatement(sql);
            
            rs = ps.executeQuery(sql);
            
            String taskName;
            String taskDescription;
            NonSerializedEquipmentTask newTask;
            
            // Loop through entire result set
            while(rs.next()){
                // Get the task name and description from each row
                taskName = rs.getString("task");
                taskDescription = rs.getString("taskDescription");
                // Create a new Task object and add to the task list
                newTask = new NonSerializedEquipmentTask(taskName,taskDescription);
                nonSerialized.put(taskName, newTask);
            }
        }
        catch (SQLException e){
            System.out.println(e.getSQLState());
        }
        finally{
            DatabaseConnector.closeQuietly(conn);
            DatabaseConnector.closeQuietly(ps);
            DatabaseConnector.closeQuietly(rs);
        }
        
        return nonSerialized;
    }
    
    
    public Map<String,SerializedEquipmentTask> getSerializedMap(){
        // Instantiate ArrayList to add tasks to
        Map<String, SerializedEquipmentTask> serializedMap = new HashMap<>();
        try{
            // Connect to database and get all rows from serialized equipment table
            conn = DatabaseConnector.getConnection();
            String sql = "select * from serialized_equipment";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            String taskName;
            String taskDescription;
            SerializedEquipmentTask newTask;
            
            // Loop through entire result set
            while(rs.next()){
                // Get the task name and description from each row
                taskName = rs.getString("model");
                taskDescription = rs.getString("description");
                // Create a new Task object and add to the task list
                newTask = new SerializedEquipmentTask(taskName, taskDescription);
                serializedMap.put(taskName, newTask);
            }
        }
        catch (SQLException e){
            System.out.println(e.getSQLState());
            System.out.println("SQL Error " + e.getMessage());
        }
        finally{
            DatabaseConnector.closeQuietly(conn);
            DatabaseConnector.closeQuietly(ps);
            DatabaseConnector.closeQuietly(rs);
        }
        return serializedMap;
    }
    
    public Map<String,StandardLaborTask> getStandardLaborMap(){
        
        Map<String,StandardLaborTask> standardMap = new HashMap<>();
        
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select * from standard_labor";
            
            ps = conn.prepareStatement(sql);
            
            rs = ps.executeQuery(sql);
            
            String taskName;
            String taskDescription;
            int payment;
            StandardLaborTask newTask;
            
            // Loop through entire result set
            while(rs.next()){
                // Get the task name, description, and payment from each row
                taskName = rs.getString("task");
                taskDescription = rs.getString("description");
                payment = rs.getInt("payment");
                // Create a new Task object and add to the task list
                /*
                    CHANGE BELOW TO STANDARD LABOR ONCE
                */
                newTask = new StandardLaborTask(taskName,taskDescription,payment);
                standardMap.put(taskName,newTask);
            }
        }
        catch (SQLException e){
            System.out.println(e.getSQLState());
            System.out.println("SQL Error " + e.getMessage());
        }
        finally{
            DatabaseConnector.closeQuietly(conn);
            DatabaseConnector.closeQuietly(ps);
            DatabaseConnector.closeQuietly(rs);
        }
        return standardMap;
    }
    
    public Map<String,SHSLaborTask> getSHSLaborMap(){
        
        Map<String,SHSLaborTask> shsLaborMap = new HashMap<>();
        
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select * from shs_labor";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            
            String taskName;
            String taskDescription;
            int payment;
            SHSLaborTask newTask;
            
            // Loop through entire result set
            while(rs.next()){
                //System.out.println("here2");
                // Get the task name and description from each row
                taskName = rs.getString("task");
                taskDescription = rs.getString("description");
                payment = rs.getInt("payment");
                // Create a new Task object and add to the task list
                newTask = new SHSLaborTask(taskName,taskDescription,payment);
                shsLaborMap.put(taskName, newTask);
            }
        }
        catch (SQLException e){
            System.out.println(e.getSQLState());
            System.out.println("SQL Error " + e.getMessage());
        }
        finally{
            DatabaseConnector.closeQuietly(conn);
            DatabaseConnector.closeQuietly(ps);
            DatabaseConnector.closeQuietly(rs);
        }
        return shsLaborMap;
    }
}
