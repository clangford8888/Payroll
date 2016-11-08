/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.gui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import payroll.DatabaseConnector;

/**
 *
 * @author Casey
 */
public class GuiDAO {
    public GuiDAO(){
    }
    
    List<CheckListItem> getActiveTechList(){
        List<CheckListItem> techList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select firstName, lastName from technician "
                        + "where active = 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                //Create new CheckListItem for each active tech
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                techList.add(new CheckListItem(firstName, lastName));
            }
        }
        catch(SQLException e){
            e.getErrorCode();
            e.getSQLState();
            e.printStackTrace();
        }
        finally{
            DatabaseConnector.closeQuietlyAll(conn, ps, rs);
        }
        return techList;
    }
    
    
}
