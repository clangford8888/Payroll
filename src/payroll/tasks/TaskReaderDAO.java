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
import java.util.List;
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
    
    
    public List<Task> getNonSerializedTable(){
        
        List<Task> nonSerialized = new ArrayList<>();
        
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select * from nonserialized_equipment";
            
            ps = conn.prepareStatement(sql);
            
            rs = ps.executeQuery(sql);
            
            String taskName;
            String taskDescription;
            Task newTask;
            
            // Loop through entire result set
            while(rs.next()){
                // Get the task name and description from each row
                taskName = rs.getString("task");
                taskDescription = rs.getString("taskDescription");
                // Create a new Task object and add to the task list
                newTask = new NonSerializedEquipmentTask(taskName,taskDescription);
                nonSerialized.add(newTask);
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
    
    public List<Task> getSerializedTable(){
        // Instantiate ArrayList to add tasks to
        List<Task> serializedList = new ArrayList<>();
        try{
            // Connect to database and get all rows from serialized equipment table
            conn = DatabaseConnector.getConnection();
            String sql = "select * from serialized_equipment";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            String taskName;
            String taskDescription;
            Task newTask;
            
            // Loop through entire result set
            while(rs.next()){
                // Get the task name and description from each row
                taskName = rs.getString("model");
                taskDescription = rs.getString("description");
                // Create a new Task object and add to the task list
                newTask = new SerializedEquipmentTask(taskName, taskDescription);
                serializedList.add(newTask);
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
        return serializedList;
    }
    
    public List<Task> getStandardLaborTable(){
        
        List<Task> nonSerialized = new ArrayList<>();
        
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select * from nonserialized_equipment";
            
            ps = conn.prepareStatement(sql);
            
            rs = ps.executeQuery(sql);
            
            String taskName;
            String taskDescription;
            int payment;
            Task newTask;
            
            // Loop through entire result set
            while(rs.next()){
                // Get the task name, description, and payment from each row
                taskName = rs.getString("task");
                taskDescription = rs.getString("taskDescription");
                payment = rs.getInt("payment");
                // Create a new Task object and add to the task list
                /*
                    CHANGE BELOW TO STANDARD LABOR ONCE
                */
                newTask = new SHSLaborTask(taskName,taskDescription,payment);
                nonSerialized.add(newTask);
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
    
    public List<Task> getSHSLaborTable(){
        
        List<Task> shsLaborList = new ArrayList<>();
        
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select * from shs_labor";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            
            String taskName;
            String taskDescription;
            int payment;
            Task newTask;
            
            // Loop through entire result set
            while(rs.next()){
                //System.out.println("here2");
                // Get the task name and description from each row
                taskName = rs.getString("task");
                taskDescription = rs.getString("description");
                payment = rs.getInt("payment");
                // Create a new Task object and add to the task list
                newTask = new SHSLaborTask(taskName,taskDescription,payment);
                shsLaborList.add(newTask);
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
        return shsLaborList;
    }
}
