/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Casey
 */
public class Payroll {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        long startTime = System.currentTimeMillis();
       
        File myFile = new File("src/payroll/input/inputFile.xls");
        PaymentFileFormatChecker checker = new PaymentFileFormatChecker(myFile);
        
        File myFile2 = new File("src/payroll/input/Payments 01-01-16 to 01-07-16.xls");
        PaymentFileFormatChecker checker2 = new PaymentFileFormatChecker(myFile2);
        
        
        checker.readFileFormat();
        //System.out.println(checker.toString());
        
        checker2.readFileFormat();
        System.out.println(checker2.toString());
        
        
        
        System.out.println(myFile.getAbsolutePath());
        PaymentParser parser = new PaymentParser(myFile2);
        
        parser.parsePaymentFile();
        parser.closeFile();
        // parser.printMap(parser.getMap());
        JobBuilder builder = new JobBuilder(checker2);
        builder.buildJobsFromMap(parser.getMap());
               
        long endTime = System.currentTimeMillis();
        
        long runTime = endTime-startTime;
        System.out.println("Run Time: "+ runTime +"ms.");
    }
    
}
