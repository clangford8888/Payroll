/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import payroll.PaymentFileFormatChecker;

/**
 * Purpose: The RowParser object is intended to accept a HSSFRow containing
 * job data and find the necessary information required to parse the row.
 * @author Casey
 */
public class RowParser {
    // Format checker to get job information index locations
    private final PaymentFileFormatChecker formatChecker;
    
    // Indicies for the relevant job information within each row.
    private final int accountNumberIndex;
    private final int workOrderIndex;
    private final int dateIndex;
    private final int techIDIndex;
    private final int customerNameIndex;
    
    // Variables that will be changed with each row processed
    private String accountNumber;
    private String workOrderNumber;
    private Date date;
    private String techID;
    private String customerName;
    
    /**
     * RowParser constructor to create RowParser objects.
     * @param inChecker PaymentFIleFormatChecker that will have the indices
     * for relevant job information.
     */
    public RowParser(PaymentFileFormatChecker inChecker){
        formatChecker = inChecker;
        accountNumberIndex = formatChecker.getAccountNumLocation();
        workOrderIndex = formatChecker.getWorkOrderLocation();
        dateIndex = formatChecker.getDateLocation();
        techIDIndex = formatChecker.getTechIDLocation();
        customerNameIndex = formatChecker.getCustNameLocation();
    }
    
    /**
     * Method to parse a row containing Job data.
     * @param inRow HSSFRow to be processed.
     */
    public void processRow(HSSFRow inRow){

        HSSFCell accountNumberCell = inRow.getCell(accountNumberIndex);
        accountNumber = accountNumberCell.getStringCellValue();      
        HSSFCell workOrderNumberCell = inRow.getCell(workOrderIndex);
        workOrderNumber = workOrderNumberCell.getStringCellValue();
        HSSFCell dateCell = inRow.getCell(dateIndex);
        date = dateCell.getDateCellValue();
        HSSFCell techIDCell = inRow.getCell(techIDIndex);
        techID = techIDCell.getStringCellValue();
        HSSFCell customerNameCell = inRow.getCell(customerNameIndex);
        customerName = customerNameCell.getStringCellValue();
        
    }
    
    public String getAccountNumber(){
        return accountNumber;
    }
    
    public String getWorkOrderNumber(){
        return workOrderNumber;
    }
    
    public Date getDate(){
        return date;
    }
    
    public String getTechID(){
        return techID;
    }
    
    public String getCustomerName(){
        return customerName;
    }
}
