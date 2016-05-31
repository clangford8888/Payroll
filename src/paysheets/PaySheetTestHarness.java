/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Casey
 */
public class PaySheetTestHarness {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try{
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            Date start = df.parse("01/02/2016");
            Date end = df.parse("01/07/2016");
            PaySheetCreatorDAO pscDAO = new PaySheetCreatorDAO();
            //pscDAO.getJobsByTech("Eric.Washington4", start, end);
            System.out.println("Finished running query.");
        }
        catch(ParseException e){
            System.out.print(e.getMessage());
        }
        
        PaySheetCreatorDAO pscDAO2 = new PaySheetCreatorDAO();
        String testGetName = pscDAO2.getTechName("Eric.Washington4");
        System.out.println("test: " + testGetName);
    }
    
}
