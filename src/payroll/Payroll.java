/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import payroll.jobs.JobFactory;
import paysheets.PaySheetCreatorDAO;

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

        /*
        PaymentFileFormatChecker checker1 = new PaymentFileFormatChecker();
        File myFile = new File("src/payroll/input/inputFile.xls");
        checker1.readFileFormat(myFile);
        System.out.println(checker1.toString());
        PaymentParser parser1 = new PaymentParser(myFile);
        parser1.parsePaymentFile();
        parser1.closeFile();
        JobFactory jFactory1 = new JobFactory(checker1);
        jFactory1.processMap(parser1.getMap());
        */
        
        PaymentFileFormatChecker checker = new PaymentFileFormatChecker();
        File myFile2 = new File("input/Payments 01-01-16 to 01-07-16.xls");
        checker.readFileFormat(myFile2);
        System.out.println(checker.toString());
        PaymentParser parser = new PaymentParser(myFile2);
        parser.parsePaymentFile();
        parser.closeFile();
        JobFactory jFactory = new JobFactory(checker);
        jFactory.processMap(parser.getMap());
        
        
        //String tempStr = "TEST";
        //Date tempDate1 = new Date();
        //Date tempDate2 = new Date();
        
        //PaySheet sheet = new PaySheet(tempStr, tempDate1, tempDate2);
        //HSSFWorkbook workbook = sheet.getWorkbook();
        //PaySheetFormatter.addJobFormatting(workbook, 2);
        //PaySheetFormatter.addJobFormatting(workbook, 4);
        
        //FileOutputStream out = new FileOutputStream("src/payroll/input/workbook.xls");
        //workbook.write(out);
        //out.close();

        long endTime = System.currentTimeMillis();
        
        long runTime = endTime-startTime;
        System.out.println("Run Time: "+ runTime +"ms.");
    }
    
}
