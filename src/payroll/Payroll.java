/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import payroll.jobs.JobBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import paysheets.PaySheet;

/**
 *
 * @author Casey
 */
public class Payroll {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException{
       
        long startTime = System.currentTimeMillis();
       
        File myFile = new File("src/payroll/input/inputFile.xls");
        PaymentFileFormatChecker checker = new PaymentFileFormatChecker();
        checker.readFileFormat(myFile);
        System.out.println(checker.toString());
        
        
        // Every time a new payment file is selected, we will need to check the format again
        File myFile2 = new File("src/payroll/input/Payments 01-01-16 to 01-07-16.xls");
        checker.readFileFormat(myFile2);
        System.out.println(checker.toString());
        

        System.out.println(myFile.getAbsolutePath());
        PaymentParser parser = new PaymentParser(myFile2);
        
        parser.parsePaymentFile();
        parser.closeFile();
        
        // parser.printMap(parser.getMap());
        JobBuilder builder = new JobBuilder(checker);
        //builder.buildJobsFromMap(parser.getMap());
        
        String tempStr = "TEST";
        Date tempDate1 = new Date();
        Date tempDate2 = new Date();
        
        PaySheet sheet = new PaySheet(tempStr, tempDate1, tempDate2);
        HSSFWorkbook workbook = sheet.getWorkbook();
        
        FileOutputStream out = new FileOutputStream("src/payroll/input/workbook.xls");
        workbook.write(out);
        out.close();
               
        long endTime = System.currentTimeMillis();
        
        long runTime = endTime-startTime;
        System.out.println("Run Time: "+ runTime +"ms.");
    }
    
}
