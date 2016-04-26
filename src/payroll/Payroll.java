/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import payroll.jobs.JobBuilder;
import java.io.File;
import payroll.tasks.TaskReader;

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
        builder.buildJobsFromMap(parser.getMap());
        
        
        //TaskReader reader = new TaskReader();
        //reader.display();
               
        long endTime = System.currentTimeMillis();
        
        long runTime = endTime-startTime;
        System.out.println("Run Time: "+ runTime +"ms.");
    }
    
}
