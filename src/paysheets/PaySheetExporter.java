/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.io.FileOutputStream;
import java.util.Date;

/**
 * Exports PaySheet workbooks into Excel format.
 * 
 * @author Casey
 */
public class PaySheetExporter {
    
    // static values to determine how output files are named
    final static int LAST_NAME_FIRST = 0;
    final static int FIRST_NAME_FIRST = 1;
    
    private int format;
    private PaySheetExporterDAO pseDAO;
    
    public PaySheetExporter(int nameFormat){
        // If input format is out of bounds, default to last name first
        if(nameFormat > 1 | nameFormat < 0){
            this.format = LAST_NAME_FIRST;
        }
        else{
            this.format = nameFormat;
        }
        this.pseDAO = new PaySheetExporterDAO();
    }
    
    /**
     * No argument default constructor sets the format to last name first
     */
    public PaySheetExporter(){
        this.format = LAST_NAME_FIRST;
        this.pseDAO = new PaySheetExporterDAO();
    }
    
    protected void exportPaySheet(FileOutputStream out, PaySheet sheet){
        // Get tech name from PaySheetExporterDAO
        String techID = sheet.getTechName();
        
        
        // Format string for file name
        String fileName = createFileName(sheet);
        System.out.println(fileName);
    }
    
    /**
     * Returns a String representation of the file name for the PaySheet being
     * exported.
     * 
     * @param techName Name of technician
     * @param sheet PaySheet being exported
     * @param format int representing which name will be displayed first
     * @return fileName String representation of the final file name
     */
    private String createFileName(PaySheet sheet){
        
        String techID = sheet.getTechID();
        String fileName;
        if(this.format == FIRST_NAME_FIRST){
            fileName = pseDAO.getNameByFirstName(techID);
        }
        else{
            fileName = pseDAO.getNameByLastName(techID);
        }
        // Add the date to end of file name
        Date startDate = sheet.getStartDate();
        Date endDate = sheet.getEndDate();
        String start = startDate.toString();
        String end = endDate.toString();
        fileName += "_" + start + "_" + end;
        
        return fileName;
    }
    
    public void setFormat(int format){
        if(format > 1 | format < 0){
            this.format = LAST_NAME_FIRST;
        }
        else{
            this.format = format;
        }
    }
}
