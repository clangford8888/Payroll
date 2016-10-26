/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        if(techID == null || start == null || end == null){
            // For now, returning null if any data is missing
            return null;
        }
        // TODO: Check if valid date range
        
        String techName = paySheetDAO.getTechName(techID);
        
        // Create a list of entries to be added to the pay sheet
        List<PaySheetEntry> entryList;
        
        entryList = paySheetDAO.getJobsByTech(techID, start, end);
        
        // Create a new pay sheet using the tech name, and start/end dates
        PaySheet newSheet = new PaySheet(techName, start, end);
        
        // Add entries to the newly created pay sheet
        
        for(PaySheetEntry p : entryList){
            newSheet.addEntry(p);
        }
        
        
        return newSheet;
    }
    
    /*
    TODO: At the end of pay sheet creation, we need to check for LEPs?
    Or should this be something that we grab from database (add to job table)
    Could do this by querying the chargeback table for the date range and return
    any jobs with the LEP tech ID.
    */
    
    
    /**
     * Creates all pay sheets for all technicians for a given date and returns
     * them as a list of PaySheets. Is the name long? Yes, but it describes
     * what is happening!
     * 
     * @param start Start of date range
     * @param end End of date range
     * @return 
     */
    public List<PaySheet> createAllTechPaySheetsByDateRange(Date start, Date end){
        // TODO: Check for stupid input dates
        List<PaySheet> paySheetList = new ArrayList<>();
        
        return paySheetList;
    }
    
}
