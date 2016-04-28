/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

/**
 *
 * @author Casey
 */
public class PaySheetCreator {
    
    PaySheetDAO paySheetDAO;
    
    public PaySheetCreator(){
        paySheetDAO = new PaySheetDAO();
    }
    
    public PaySheet createPaySheet(){
        
        PaySheet newSheet = new PaySheet();
        
        return newSheet;
    }
    
}
