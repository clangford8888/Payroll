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
       
        //File myFile = new File("src/payroll/input/inputFile.xls");
        
        PaymentFileFormatChecker checker = new PaymentFileFormatChecker();
        
        //checker.readFileFormat(myFile);
        //System.out.println(checker.toString());
        //System.out.println(myFile.getAbsolutePath());
        
        
        // Every time a new payment file is selected, we will need to check the format again
        File myFile2 = new File("src/payroll/input/Payments 01-01-16 to 01-07-16.xls");
        checker.readFileFormat(myFile2);
        System.out.println(checker.toString());
        
        PaymentParser parser = new PaymentParser(myFile2);
        
        //parser.parsePaymentFile();
        parser.closeFile();
        
        // parser.printMap(parser.getMap());
        //JobBuilder builder = new JobBuilder(checker);
        //builder.buildJobsFromMap(parser.getMap());
        
        JobFactory jFactory = new JobFactory(checker);
        //jFactory.processMap(parser.getMap());
        
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
        
        
        try{
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date start = df.parse("01/02/2016");
            Date end = df.parse("01/07/2016");
            PaySheetCreatorDAO pscDAO = new PaySheetCreatorDAO();
            pscDAO.getJobsByTech("Eric.Washington4", start, end);
            System.out.println("Finished running query.");
        }
        catch(ParseException e){
            System.out.print(e.getMessage());
        }
        
        PaySheetCreatorDAO pscDAO2 = new PaySheetCreatorDAO();
        String testGetName = pscDAO2.getTechName("Eric.Washington4");
        System.out.println("test: " + testGetName);
               
        long endTime = System.currentTimeMillis();
        
        long runTime = endTime-startTime;
        System.out.println("Run Time: "+ runTime +"ms.");
    }
    
}
