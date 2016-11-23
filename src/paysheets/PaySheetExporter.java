/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
    private final PaySheetExporterDAO pseDAO;
    
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
    
    public void exportPaySheet(File outputDirectory, PaySheet sheet){
        // Get tech name from PaySheetExporterDAO
        String techID = sheet.getTechName();

        // Format string for file name
        String outputFileName = createFileName(sheet);
        String directoryPath = outputDirectory.getPath();
        String outputFilePath = createOutputFilePath(directoryPath, outputFileName);
        System.out.println("Full output path:\n" + outputFilePath);
        try{
            FileOutputStream outputStream = new FileOutputStream(outputFilePath);
            writePaySheet(outputStream, sheet);
            outputStream.close();
        }
        catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "The file could not be created at this location!",
                                            "Alert!", JOptionPane.ERROR_MESSAGE);
            // TODO log error
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void writePaySheet(FileOutputStream ouputStream, PaySheet sheet){
        HSSFWorkbook workbook = sheet.getWorkbook();
        if(workbook != null){
            try{
                workbook.write(ouputStream);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Returns a String representation of the file name for the PaySheet being
     * exported.
     *
     * @param sheet PaySheet being exported
     * @return fileName String representation of the final file name
     */
    private String createFileName(PaySheet sheet){
        
        String techID = sheet.getTechID();
        String fileName;
        if(this.format == FIRST_NAME_FIRST){
            fileName = pseDAO.getNameFromDatabaseByFirstName(techID);
        }
        else{
            fileName = pseDAO.getNameFromDatabaseByLastName(techID);
        }
        // Add the date to end of file name
        Date startDate = sheet.getStartDate();
        Date endDate = sheet.getEndDate();
        String dateString = formatDateString(startDate, endDate);
        fileName += "_" + dateString + ".xls";
        return fileName;
    }
    
    /**
     * Returns a full output file path given a directory path and an output file
     * name.
     * 
     * @param directoryPath
     * @param outputFileName
     * @return 
     */
    private String createOutputFilePath(String directoryPath, String outputFileName){
        return directoryPath + "\\" + outputFileName;
    }
    
    /**
     * Takes two dates and returns the string representation using a simple
     * date format
     * @param start Start Date
     * @param end End Date
     * @return fileName String representation to be appended to fileName
     */
    private String formatDateString(Date start, Date end){
        
        String fileName = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-DD-yy");
        String startString = dateFormat.format(start);
        String endString = dateFormat.format(end);
        fileName = startString + "_" + endString;
        
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
