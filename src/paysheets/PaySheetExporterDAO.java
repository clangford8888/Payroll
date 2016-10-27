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
    
    // static values to determine how output files are named
    final static int LAST_NAME_FIRST = 0;
    final static int FIRST_NAME_FIRST = 1;
    
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
            String sql = "select name "
                        + "from technician "
                        + "where techID = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, techID);
            rs = ps.executeQuery();
            while(rs.next()){
                techName = rs.getString("name");
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
}
