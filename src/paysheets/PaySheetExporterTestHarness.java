/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Casey
 */
public class PaySheetExporterTestHarness {
    
    public static void main(String[] args){
        PaySheetExporterDAO pseDAO = new PaySheetExporterDAO();
        
        String test = pseDAO.getTechNameFromDatabase("Angel.Torres1");
        System.out.println(test);
        String test1 = pseDAO.getTechNameFromDatabase("Jhonny.Gonzalez3");
        System.out.println(test1);
        String test2 = pseDAO.getTechNameFromDatabase("david.rose");
        System.out.println(test2);
        String test3 = pseDAO.getTechNameFromDatabase("allen.kerr");
        System.out.println(test3);
        System.out.println();
        
        PaySheetExporterTestHarness helper = new PaySheetExporterTestHarness();
        try{
            helper.testCreateFileNames();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
    public void testCreateFileNames() throws FileNotFoundException, IOException{
        System.out.println("Running testCreateFileNames()");
        try{
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date start = df.parse("01/02/2016");
            Date end = df.parse("01/07/2016");
            List<PaySheetEntry> list;
            PaySheetCreatorDAO pscDAO = new PaySheetCreatorDAO();
            list = pscDAO.getJobsByTech("Eric.Washington4", start, end);
            
            PaySheetCreator psc = new PaySheetCreator();
            
            PaySheet testPaySheet = psc.createPaySheet("Eric.Washington4", start, end);
            
           
            
            PaySheetExporter lastName = new PaySheetExporter(PaySheetExporter.LAST_NAME_FIRST);
            FileOutputStream out = new FileOutputStream("src/payroll/input/TestWorkbook2.xls");
        
            //lastName.exportPaySheet(out , testPaySheet);
        
        
            //HSSFWorkbook workbook = testPaySheet.getWorkbook();

            //FileOutputStream out = new FileOutputStream("src/payroll/input/TestWorkbook.xls");
            //workbook.write(out);
            
            out.close();

            
            //PRINTS ALL JOBS AND THEIR DATA
            //for(PaySheetEntry p : list){
            //    System.out.println(p.toString());
            //    p.printEquipmentLists();
           //     System.out.println();
            //}
            
        }
        catch(ParseException e){
            System.out.print(e.getMessage());
        }
        
        
    }
}
