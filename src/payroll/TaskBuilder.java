/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 *
 * @author Casey
 */
public class TaskBuilder {
    
    public static Task createTask(HSSFRow inRow, PaymentFileFormatChecker checker){
        
        int taskTypeIndex = checker.getTaskTypeLocation();
        int taskNameIndex = checker.getTaskNameLocation();
        int taskDescriptionIndex = checker.getTaskDescriptionLocation();
        
        
        HSSFRow row = inRow;
        // Select the cell at the Task Type Position
        HSSFCell taskTypeCell = row.getCell(taskTypeIndex);
        // Get the task type and store the value
        String taskType = getStringCellValue(taskTypeCell);
        // Get task name and store value
        HSSFCell taskNameCell = row.getCell(taskNameIndex);
        String taskName = getStringCellValue(taskNameCell);
        // Get task description and store value
        HSSFCell taskDescriptionCell = row.getCell(taskDescriptionIndex);
        String taskDescription = getStringCellValue(taskDescriptionCell);
        
        
        // TODO: Break this into it's own method??
        // If the task type represents equipment
        if(taskType.equals("E")){
            // Check if taskName is Non-Serialized
            if(isNonSerializedEquipment(taskName)){
                // Return new Unserialized equipment
                return new NonSerializedEquipmentTask(taskName,taskDescription);
            }
            else{
                // Use the task description to see if equipment is serialized
                // Task name is not used, because it will contain the serial #
                if(isSerializedEquipment(taskDescription)){
                    // Return new Seriallized equipment
                    return new SerializedEquipmentTask(taskName, taskDescription);
                }
            }   
        }
        // Else If task type indicates Labor task
        else if(taskType.equals("L")){
            // If task name is standard labor task
            if(isStandardLaborTask(taskName)){
                // Create new Standard Labor Task
                StandardLaborTask stdLaborTask = new StandardLaborTask(
                                                    taskName, taskDescription);
                // Look up the labor payment
                int pay = stdLaborTask.lookupLaborPayment();
                // Set the labor payment
                stdLaborTask.setPayment(pay);
                // Return the Standard Labor Task
                return stdLaborTask;
            }
            // Else return new Smart Home Service task
            else if(isSHSLaborTask(taskName)){
                // Create new SHS Labor Task
                SHSLaborTask shsTask = new SHSLaborTask(taskName, taskDescription);
                // Look up the labor payment
                int pay = shsTask.lookupLaborPayment();
                // Set the labor payment
                shsTask.setPayment(pay);
                // Return the new SHS Task
                return shsTask;
            }
        }
        // IF ALL ELSE FAILS, RETURN NULL.
        // At this time only adding task to Task List if not null
        // Checking for null in JobBuilder
        return null;
    }
    
    // Method checks the type of cell and returns the value as a String
    private static String getStringCellValue(HSSFCell inCell){
        HSSFCell cell = inCell;
        String value;
        
        // Check to see if cell is numeric (Receiver) or not (Could be anything)
        int cellType = cell.getCellType();
        // If cell is numeric
        if(cellType == 0){
            // Get numeric cell's value as a Double
            Double cellValue = cell.getNumericCellValue();
            // Convert double value to string to be able to instantiate task
            value = Double.toString(cellValue);
        }
        // Else get cell's value as String
        else{
            value = cell.getStringCellValue();
        }
        return value;
    }
    
    private static boolean isNonSerializedEquipment(String inTaskName){
        String taskName = inTaskName;
        String sql = "select exists(select 1 from nonserialized_equipment where task = ? )";
        
        return checkIfTaskExists(sql,taskName);
    }
    
    private static boolean isSerializedEquipment(String inTaskDescription){
        String taskDescription = inTaskDescription;
        String sql = "select exists(select 1 from serialized_equipment where model = ? )";
        
        return checkIfTaskExists(sql,taskDescription);
    }
    
    private static boolean isStandardLaborTask(String inTaskName){
        String taskName = inTaskName;
        String sql = "select exists(select 1 from standard_labor where task = ? )";
        
        return checkIfTaskExists(sql, taskName);
    }
    
    private static boolean isSHSLaborTask(String inTaskName){
        String taskName = inTaskName;
        String sql = "select exists(select 1 from shs_labor where task = ? )";
        
        return checkIfTaskExists(sql, taskName);
    }
    
    private static boolean checkIfTaskExists(String inSql, String inTask){
        // TODO: COMMENTS!!
        String sql = inSql;
        String task = inTask;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try{
            // TODO: COMMENTS!
            conn = DatabaseConnector.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, task);
            rs = ps.executeQuery();
            int result = 0;
            if(rs.next()){
                result = rs.getInt(1);
            }
            if(result > 0){
                return true;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        finally{
            DatabaseConnector.closeQuietly(rs);
            DatabaseConnector.closeQuietly(ps);
            DatabaseConnector.closeQuietly(conn);
        }
        // If not Standard labor, return false
        return false;
    }
}

