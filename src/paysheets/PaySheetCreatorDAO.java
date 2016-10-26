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
    
    
    public PaySheetCreatorDAO(){

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
        
        Connection conn = null; 
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            
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
            
            conn = DatabaseConnector.getConnection();
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
                getSerializedEquipmentFromDatabase(newEntry);
                getNonSerializedEquipmentFromDatabase(newEntry);
                getSHSEquipmentFromDatabase(newEntry);
                
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
     * Queries the database and retrieves all Serialized Equipment. Adds all 
     * retrieved equipment to the PaySheetEntry object's serialized list.
     * 
     * @param newEntry the PaySheetEntry the equipment is being retrieved for.
     * @return None. All equipment added to the PaySheetEntry's list.
     */
    private void getSerializedEquipmentFromDatabase(PaySheetEntry newEntry){
        
        Connection conn = null; 
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            // Set up SQL statement to query database for serialized equipment
            String sql =    "select Model, serial_caid " +
                            "from consumed_equip_serialized " +
                            "where workOrderNum = ?";
            conn = DatabaseConnector.getConnection();
            ps = conn.prepareStatement(sql);
            String workOrder = newEntry.getWorkOrderNumber();
            ps.setString(1, workOrder);
            rs = ps.executeQuery();
            // Process the result set and add it to the entry.
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
        
        Connection conn = null; 
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String sql =    "select t1.model, t1.quantity " +
                            "from consumed_equip_nonserialized as t1 " +
                            " inner join nonserialized_equipment as t2 " +
                            " on t1.model = t2.taskDescription " +
                            "where t1.workOrderNum = ? and t2.SHS < 1";
                 
            conn = DatabaseConnector.getConnection();
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
        
    /**
     * Queries the database and retrieves all SHS Equipment. Adds all retrieved
     * equipment to the PaySheetEntry object's SHS list.
     * 
     * @param newEntry PaySheetEntry the method retrieves equipment for.
     */
    private void getSHSEquipmentFromDatabase(PaySheetEntry newEntry){
        Connection conn = null; 
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            String sql =    "select t1.model, t1.quantity " +
                            "from consumed_equip_nonserialized as t1 " +
                            " inner join nonserialized_equipment as t2 " +
                            " on t1.model = t2.taskDescription " +
                            "where t1.workOrderNum = ? and t2.SHS > 0";
                 
            conn = DatabaseConnector.getConnection();
            ps = conn.prepareStatement(sql);
            String workOrder = newEntry.getWorkOrderNumber();
            ps.setString(1, workOrder);
            rs = ps.executeQuery();
            // Process the result set and equipment to the equipment list
            while(rs.next()){
                String nonSerialModel = rs.getString("model");
                int quantity = rs.getInt("quantity");
                newEntry.addSHS(quantity + ": " + nonSerialModel);
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
        String techName = "";   // Name of technician to be returned
        Connection conn = null; 
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            // Define the query and initialize the prepared statement
            String sql = "select name from technician "
                    + " where techID = ?";
            conn = DatabaseConnector.getConnection();
            ps = conn.prepareStatement(sql);     
            ps.setString(1, techID);
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
