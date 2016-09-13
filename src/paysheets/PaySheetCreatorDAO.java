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

            while(rs.next()){
                String workOrder = rs.getString("workOrderNum");
                String accountNumber = rs.getString("accountNum");
                Date woDate = rs.getDate("date");
                String designation = rs.getString("designation");
                String customer = rs.getString("customerName");
                int payment = rs.getInt("payment");
                
                // Created new PaySheetEntry from result
                newEntry = new PaySheetEntry(woDate, workOrder, customer, 
                                        designation, payment);
                
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
     * @return nothing. All Equipment added to the PaySheetEntry object
     */
    private void getEquipmentData(PaySheetEntry newEntry){
        getSerializedEquipmentFromDatabase(newEntry);
        getNonSerializedEquipmentFromDatabase(newEntry);
            
            // Set up and query the database for the SHS-list
            /*
            TODO: QUERY THE DATABASE FOR SHS EQUIPMENT!
            */
        
        
    }
    
    /**
     * Queries the database and retrieves all Serialized Equipment. Adds all 
     * retrieved equipment to the PaySheetEntry object's serialized list.
     * 
     * @param newEntry the PaySheetEntry the equipment is being retrieved for.
     * @return None. All equipment added to the PaySheetEntry's list.
     */
    private void getSerializedEquipmentFromDatabase(PaySheetEntry newEntry){
        try{
            // Set up SQL statement to query database for serialized equipment
            conn = DatabaseConnector.getConnection();
            // Query database for model and R00# for the given WO#
            String sql =    "select Model, serial_caid " +
                            "from consumed_equip_serialized " +
                            "where workOrderNum = ?";
            ps = conn.prepareStatement(sql);
            String workOrder = newEntry.getWorkOrderNumber();
            ps.setString(1, workOrder);
            rs = ps.executeQuery();
            
            while(rs.next()){
                String model = rs.getString("Model");
                String receiverNumber = rs.getString("serial_caid");
                // Put model & R00# together, then add to the serialized list
                String serializedEquipment = model + ": " + receiverNumber;
                newEntry.addSerialized(serializedEquipment);                
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
    }
    
    /**
     * Queries the database and retrieves all Non-Serialized Equipment. Adds 
     * all retrieved equipment to the PaySheetEntry object's non-serial list.
     * 
     * @param newEntry the PaySheetEntry the equipment is being retrieved for.
     * @return None. All equipment added to the PaySheetEntry's list.
     */
    private void getNonSerializedEquipmentFromDatabase(PaySheetEntry newEntry){
        try{
            String sql =   "select model, quantity " +
                    "from consumed_equip_nonserialized " +
                    "where workOrderNum = ?";
            ps = conn.prepareStatement(sql);
            String workOrder = newEntry.getWorkOrderNumber();
            ps.setString(1, workOrder);
            rs = ps.executeQuery();
            // Process the result set and equipment to the equipment list
            while(rs.next()){
                String nonSerialModel = rs.getString("model");
                int quantity = rs.getInt("quantity");
                newEntry.addNonSerialized(quantity + ": " + nonSerialModel);
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
