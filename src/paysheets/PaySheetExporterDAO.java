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
import payroll.DatabaseConnector;

/**
 *
 * @author Casey
 */
public class PaySheetExporterDAO {
    
    public PaySheetExporterDAO(){
    }
    
    protected String getTechName(String techID){
        // Set techName to input techID in case DB does not return a result;
        String techName = techID;
        // Declare JDBC components
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select firstName "
                        + "from technician "
                        + "where techID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, techID);
            rs = ps.executeQuery();
            while(rs.next()){
                techName = rs.getString("firstName");
            }
        }
        catch(SQLException e){
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
        finally{
            DatabaseConnector.closeQuietlyAll(conn, ps, rs);
        }
        
        return techName;
    }
    
    /**
     * Returns a String representation of a file name using the first name then
     * the last name.
     * 
     * @param techID ID of the technician
     * @return fileName
     */
    protected String getNameByFirstName(String techID){
        // Set a default file name so we don't return a null value
        String defaultFileName = "TECH_NOT_FOUND";
        String fileName = defaultFileName;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select firstName, lastName "
                        + "from technician "
                        + "where techID = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, techID);
            rs = ps.executeQuery();
            while(rs.next()){
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                fileName = firstName + "_" + lastName;
            }
        }
        catch(SQLException e){
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
        finally{
            DatabaseConnector.closeQuietlyAll(conn, ps, rs);
        }
        // Return the file name after query
        return fileName;
    }
    
    /**
     * Returns a String representation of a file name using the last name then
     * the first name.
     * 
     * @param techID ID of the technician
     * @return fileName
     */
    protected String getNameByLastName(String techID){
        System.out.println(techID);
        // Set a default file name so we don't return a null value
        String defaultFileName = "TECH_NOT_FOUND";
        String fileName = defaultFileName;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select lastName, firstName "
                        + "from technician "
                        + "where techID = ? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, techID);
            rs = ps.executeQuery();
            while(rs.next()){
                String lastName = rs.getString("lastName");
                String firstName = rs.getString("firstName");
                fileName = lastName + "_" + firstName;
                System.out.println(fileName);
            }
        }
        catch(SQLException e){
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        }
        finally{
            DatabaseConnector.closeQuietlyAll(conn, ps, rs);
        }
        // Return the file name after query
        return fileName;
    }
}
