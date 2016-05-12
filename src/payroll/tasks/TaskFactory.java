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
    
    TaskReader masterTaskList;
    
    public TaskFactory(TaskReader inTaskReader){
        masterTaskList = inTaskReader;
    }
    
    public void createTask(Job inJob, HSSFRow inRow,
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
        
        Task newTask;
        // New approach: Search for task by description, since the R00# is the
        // task name in the payment file. If we search for Task based on name,
        // it will not show up since the R00#'s are unique.
        
        // If the task type represents equipment
        if(taskType.equals("E")){
            // Check if taskName is Non-Serialized
            System.out.println("Task Name: " + taskDescription);
            newTask = masterTaskList.getEquipmentTask(taskName, taskDescription);
            
            if(newTask instanceof payroll.tasks.NonSerializedEquipmentTask){
                inJob.addNonSerializedEquipmentTask((NonSerializedEquipmentTask)newTask);
            }
            else if(newTask instanceof payroll.tasks.SerializedEquipmentTask){
                System.out.println("Serialized!");
                SerializedEquipmentTask newSerialized = (SerializedEquipmentTask)newTask;
                newSerialized.setSerialNumber(taskDescription);
                inJob.addSerializedEquipmentTask((SerializedEquipmentTask)newSerialized);
            }
            // If newTask was null, it will not be added to the task list
        }
        // Else If task type indicates Labor task
        else if(taskType.equals("L")){
            newTask = masterTaskList.getLaborTask(taskName);
            // If task name is standard labor task
            if(newTask instanceof StandardLaborTask){
                inJob.addStandardLaborTask((StandardLaborTask)newTask);
            }
            else if(newTask instanceof SHSLaborTask){
                inJob.addSHSLaborTask((SHSLaborTask)newTask);
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

}

