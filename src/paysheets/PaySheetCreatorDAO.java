/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paysheets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import payroll.DatabaseConnector;
import payroll.jobs.Job;

/**
 *
 * @author Casey
 */
public class PaySheetCreatorDAO {
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    public PaySheetCreatorDAO(){
        conn = null;
        ps = null;
        rs = null;
    }
    
    /**
     * Queries the database to get all jobs completed by a tech within the
     * date range from the given parameters.
     * 
     * @param tech String ID of the technician the method is retrieving jobs for
     * @param start Date Start of the date range.
     * @param end Date End of the date range.
     * @return List Returns a List of all PaySheetEntries that will be added to
     *              a pay sheet.
     */
    protected List<PaySheetEntry> getJobsByTech(String tech, Date start, Date end){
        
        PaySheetEntry newEntry;
        List<PaySheetEntry> jobList = new ArrayList<>();
        
        try{
            conn = DatabaseConnector.getConnection();
            // Set the string version of the query to be used
            String sql =    "select * from job " +
                            "where techID = ? " +
                            "and (date between ? and ?)" +
                            "order by date";
            // Convert the input Dates to SQL Date format
            long startTimeAsLong = start.getTime();
            java.sql.Date sqlStart = new java.sql.Date(startTimeAsLong);
            long endTimeAsLong = end.getTime();
            java.sql.Date sqlEnd = new java.sql.Date(endTimeAsLong);
            
            ps = conn.prepareStatement(sql);     
            ps.setString(1, tech);
            ps.setDate(2, sqlStart);
            ps.setDate(3, sqlEnd);
            rs = ps.executeQuery();
            
            String workOrder;
            String accountNumber; // Not used
            Date woDate;
            String designation;
            String customer;
            int payment;

            while(rs.next()){
                workOrder = rs.getString("workOrderNum");
                accountNumber = rs.getString("accountNum");
                woDate = rs.getDate("date");
                designation = rs.getString("designation");
                customer = rs.getString("customerName");
                payment = rs.getInt("payment");
                
                // Created new PaySheetEntry from result
                newEntry = new PaySheetEntry(woDate, workOrder, customer, 
                                        designation, payment, "None");
                
                /*
                TODO:   Query database and get SHS data
                        Get LEP data
                */
                getEquipmentData(newEntry);
                
                // Add new entry to list
                jobList.add(newEntry);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL state: " + e.getSQLState());
            System.out.println("Error code: " + e.getErrorCode());
        }
        finally{
            DatabaseConnector.closeQuietly(conn);
            DatabaseConnector.closeQuietly(ps);
            DatabaseConnector.closeQuietly(rs);
        }
        
        return jobList;
    }
    
    /**
     * Look up serialized equipment, non-serialized equipment, and SHS equipment
     * and add them to the newly created PaySheetEntry
     * 
     * @param newEntry PaySheetEntry whose
     * @return List String list containing all serialized equipment used.
     */
    private void getEquipmentData(PaySheetEntry newEntry){
        
        String workOrder = newEntry.getWorkOrderNumber();
        // Model is receiver model number, receiver number is the serial CAID
        String model;
        String receiverNumber;
        String serializedEquipment;
        // Didn't like me using the same result set while the other was open
        // from the calling method.
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;
        
        try{
            // Set up SQL statement to query database for serialized equipment
            conn = DatabaseConnector.getConnection();
            // Query database for model and R00# for the given WO#
            String sql =    "select Model, serial_caid " +
                            "from consumed_equip_serialized " +
                            "where workOrderNum = ?";
            ps2 = conn.prepareStatement(sql);
            ps2.setString(1, workOrder);
            rs2 = ps2.executeQuery();
            
            while(rs2.next()){
                model = rs2.getString("Model");
                receiverNumber = rs2.getString("serial_caid");
                // Put model & R00# together, then add to the serialized list
                serializedEquipment = model + ": " + receiverNumber;
                newEntry.addSerialized(serializedEquipment);                
            }
            // Set up and query the database for the non-serialized eqt
            String nonSerialModel;  // Model of nonserialized items used
            int quantity;           // Quantity of nonserialized items used
            // Using same sql variable for next query
            sql =   "select model, quantity " +
                    "from consumed_equip_nonserialized " +
                    "where workOrderNum = ?";
            ps2 = conn.prepareStatement(sql);
            ps2.setString(1, workOrder);
            rs2 = ps2.executeQuery();
            // Process the result set and equipment to the equipment list
            while(rs2.next()){
                nonSerialModel = rs2.getString("model");
                quantity = rs2.getInt("quantity");
                newEntry.addNonSerialized(quantity + ": " + nonSerialModel);
            }
            
            // Set up and query the database for the SHS-list
            /*
            TODO: QUERY THE DATABASE FOR SHS EQUIPMENT!
            */
            
            
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL state: " + e.getSQLState());
            System.out.println("Error code: " + e.getErrorCode());
        }
        finally{
            DatabaseConnector.closeQuietly(conn);
            DatabaseConnector.closeQuietly(ps2);
            DatabaseConnector.closeQuietly(rs2);
        }
        
    }
    
    protected List<Job> getJobsByWeek(){
        List<Job> jobList = new ArrayList<>();
        
        return jobList;
    }
    
    
    /**
     * Queries the database and returns the technician's name.
     * 
     * Will return null if the techID is not found in the DB.
     * 
     * @param techID  ID of technician whose name we want, not null
     * @return techName
    */
    protected String getTechName(String techID){
        
        String techName = "";
        
        try{
            conn = DatabaseConnector.getConnection();
            // Define the query and initialize the prepared statement
            String sql = "select name from technician "
                    + " where techID = ?";
            ps = conn.prepareStatement(sql);     
            ps.setString(1, techID);
            // Execute the query
            rs = ps.executeQuery();
            // Get the techName from the result set
            while(rs.next()){
                techName = rs.getString("name");
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL state: " + e.getSQLState());
            System.out.println("Error code: " + e.getErrorCode());
        }
        finally{
            DatabaseConnector.closeQuietly(conn);
            DatabaseConnector.closeQuietly(ps);
            DatabaseConnector.closeQuietly(rs);
        }
        return techName;
    }
    
}
