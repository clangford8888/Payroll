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
 *
 * @author Casey
 */
public class RowParser {
    
    private final PaymentFileFormatChecker formatChecker;
       
    private HSSFRow row;
    private String accountNumber;
    private String workOrderNumber;
    private Date date;
    private String techID;
    private String customerName;
    
    public RowParser(PaymentFileFormatChecker inChecker){
        formatChecker = inChecker;
    }
    
    public void processRow(HSSFRow inRow){
        
        row = inRow;
        
        int accountNumberIndex = formatChecker.getAccountNumLocation();
        int workOrderIndex = formatChecker.getWorkOrderLocation();
        int dateIndex = formatChecker.getDateLocation();
        int techIDIndex = formatChecker.getTechIDLocation();
        int customerNameIndex = formatChecker.getCustNameLocation();
        
        HSSFCell accountNumberCell = row.getCell(accountNumberIndex);
        accountNumber = accountNumberCell.getStringCellValue();      
        HSSFCell workOrderNumberCell = row.getCell(workOrderIndex);
        workOrderNumber = workOrderNumberCell.getStringCellValue();
        HSSFCell dateCell = row.getCell(dateIndex);
        date = dateCell.getDateCellValue();
        HSSFCell techIDCell = row.getCell(techIDIndex);
        techID = techIDCell.getStringCellValue();
        HSSFCell customerNameCell = row.getCell(customerNameIndex);
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
