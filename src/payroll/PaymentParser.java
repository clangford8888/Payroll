/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;


/**
 *
 * @author Casey
 */
public class PaymentParser {
    
    // Declare variables.
    
    File inputFile;
    FileInputStream inputStream;
    HSSFWorkbook workbook;
    HSSFSheet sheet;
    int lastRow;
    String[] headings;
    HashMap<String, ArrayList<HSSFRow>> inputMap;
    ArrayList<HSSFRow> rowList;
    
    // Constructor takes in an Excel file, sets up components
    public PaymentParser(File inFile){
        
        this.inputFile = inFile;
        
        try{
            this.inputStream = new FileInputStream(inputFile);
            this.workbook = new HSSFWorkbook(inputStream);
            this.sheet = workbook.getSheetAt(0);
        }
        catch(FileNotFoundException ex){
            System.out.println(ex.getMessage());
            System.out.println("FILE NOT FOUND!!");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        
        // Create input map to hold lines read from Excel file
        this.inputMap = new HashMap<>();
        this.rowList = new ArrayList<>();
    }
    
    // TODO *********** ADD SOME COMMENTS *************************
    /*
    Purpose: Read entire input file, store data where appropriate.
    */
    public void parsePaymentFile(){
        
        PaymentFileFormatChecker checker = new PaymentFileFormatChecker(inputFile);
        
        checker.readFileFormat();
        int workOrderIndex = checker.getWorkOrderLocation();
        String workOrderNum;
        
  
        int rowIndex = 0;
        HSSFRow currentRow;
        lastRow = sheet.getLastRowNum();
        
        
        // Check file format before parsing data. Ensure file has at least one
        //  row of data before attempting to parse an empty file.
        if(rowIndex < lastRow){
            
            for(int i = rowIndex+1; i < lastRow+1; i++){
                
                currentRow = sheet.getRow(i);
                
                Cell workOrderCell = currentRow.getCell(workOrderIndex);
                workOrderNum = workOrderCell.getStringCellValue();
                
                // If map contains WO#, add the current row to that WO#'s list
                if(inputMap.containsKey(workOrderNum)){
                    // Get Array List for current WO#, add current row to list
                    inputMap.get(workOrderNum).add(currentRow);
                }
                else{
                    // WO# is not contained in map, so add it, along with row
                    ArrayList<HSSFRow> list = new ArrayList<>();
                    list.add(currentRow);
                    inputMap.put(workOrderNum, list);
                }
            }
        }
        else{
            /*
            TODO: Add method to force quit or select a new file that works
            */
            System.out.println("File is empty!");
        }
    }
    
    // ONLY PRINTS THE KEYS AND COUNT!
    public void printMap(HashMap<String, ArrayList<HSSFRow>> map){
        int count = 0;
        for(String wo : map.keySet()){
            count++;
            System.out.println(wo + ". #KEYS: " + count);
        } 
    }
    
    public void closeFile(){
        try{
            inputStream.close();
        }
        catch(IOException e){
            System.out.println("Something went wrong trying to close the file!");
            System.out.println(e.getMessage());
        }
    }
    
    /*
    Getter method for the PaymentParser's HashMap. Allows builder class to 
    create jobs.
    */
    public HashMap<String, ArrayList<HSSFRow>> getMap(){
        return inputMap;
    }
}
