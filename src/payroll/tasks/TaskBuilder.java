/**
 * THIS IS THE OLD APPROACH. Will remove later.
 */
package payroll.tasks;

import payroll.jobs.Job;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import payroll.DatabaseConnector;
import payroll.PaymentFileFormatChecker;

/**
 *
 * @author Casey
 */
public class TaskBuilder {
    
    public static void createTask(Job inJob, HSSFRow inRow,
                                    PaymentFileFormatChecker checker){
        
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
                // Create new Serialized Equipment Task
                NonSerializedEquipmentTask newTask = 
                        new NonSerializedEquipmentTask(taskName,taskDescription);
                // Add task to appropriate task list
                //inJob.addNonSerializedEquipmentTask(newTask);
            }
            else{
                // Use the task description to see if equipment is serialized
                // Task name is not used, because it will contain the serial #
                if(isSerializedEquipment(taskDescription)){
                    // Create new Serialized Equipment Task
                    SerializedEquipmentTask newTask = 
                        new SerializedEquipmentTask(taskName, taskDescription);
                    // Add task to appropriate task list
                    //inJob.addSerializedEquipmentTask(newTask);
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
                // Add Standard Labor Task to job
                inJob.addStandardLaborTask(stdLaborTask);
            }
            // Else return new Smart Home Service task
            else if(isSHSLaborTask(taskName)){
                // Create new SHS Labor Task
                SHSLaborTask shsTask = new SHSLaborTask(taskName, taskDescription);
                // Look up the labor payment
                int pay = shsTask.lookupLaborPayment();
                // Set the labor payment
                shsTask.setPayment(pay);
                // Add SHS Labor Task to job
                inJob.addSHSLaborTask(shsTask);
            }
        }
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

