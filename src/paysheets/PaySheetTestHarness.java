/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

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
public class PaySheetTestHarness {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        
        try{
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date start = df.parse("01/02/2016");
            Date end = df.parse("01/07/2016");
            List<PaySheetEntry> list;
            PaySheetCreatorDAO pscDAO = new PaySheetCreatorDAO();
            list = pscDAO.getJobsByTech("Eric.Washington4", start, end);
            
            PaySheetCreator psc = new PaySheetCreator();
            
            PaySheet testPaySheet = psc.createPaySheet("Eric.Washington4", start, end);
            
            HSSFWorkbook workbook = testPaySheet.getWorkbook();

            FileOutputStream out = new FileOutputStream("src/payroll/input/TestWorkbook.xls");
            workbook.write(out);
            out.close();

            
            //PRINTS ALL JOBS AND THEIR DATA
            for(PaySheetEntry p : list){
                System.out.println(p.toString());
                p.printEquipmentLists();
                System.out.println();
            }
            
        }
        catch(ParseException e){
            System.out.print(e.getMessage());
        }
        
        //PaySheetCreatorDAO pscDAO2 = new PaySheetCreatorDAO();
        //String testGetName = pscDAO2.getTechName("Eric.Washington4");
        //System.out.println("test: " + testGetName);
    }
    
}
