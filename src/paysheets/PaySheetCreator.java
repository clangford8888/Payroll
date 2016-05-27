/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.util.Date;

/**
 *
 * @author Casey
 */
public class PaySheetCreator {
    
    PaySheetCreatorDAO paySheetDAO;
    
    public PaySheetCreator(){
        paySheetDAO = new PaySheetCreatorDAO();
    }
    
    public PaySheet createPaySheet(String techID, Date start, Date end){
        // Check nulls
        
        PaySheet newSheet = new PaySheet(techID, start, end);
        
        return newSheet;
    }
    
}
