/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.tasks;

import payroll.jobs.Job;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import payroll.PaymentFileFormatChecker;

/**
 *
 * @author Casey
 */
public class TaskFactory {
    
    TaskCache masterTaskList;
    PaymentFileFormatChecker checker;
    
    public TaskFactory(TaskCache inTaskReader, PaymentFileFormatChecker inChecker){
        this.masterTaskList = inTaskReader;
        this.checker = inChecker;
    }
     
    public Task createTask(HSSFRow inRow){
        
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

        // If the task type represents equipment
        if(taskType.equals("E")){
            // Check if taskName is Non-Serialized
            EquipmentTask eTask = masterTaskList.getEquipmentTask(taskName, taskDescription);
            
            return eTask;

        }
        // Else If task type indicates Labor task
        else if(taskType.equals("L")){
            LaborTask lTask = masterTaskList.getLaborTask(taskName);
            
            return lTask;
        }     
        // TODO: RETURN NULL OBJECT!
        else{
            return null;
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

}

