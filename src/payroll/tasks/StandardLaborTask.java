/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import payroll.DatabaseConnector;

/**
 *
 * @author Casey
 */
public class StandardLaborTask extends LaborTask{
    
    private final String advancedTaskType;
    
    public StandardLaborTask(String inName, String inDescription){
        super(inName, inDescription);
        this.advancedTaskType = "Standard Labor";
        this.setTaskType(advancedTaskType);
    }
    
    @Override
    public String getAdvancedTaskType(){
        return advancedTaskType;
    }
    
    // TODO: COMMENTS
    // TODO: Maybe move database calls and stuff to super class?
    @Override
    public int lookupLaborPayment(){
        int pay = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select payment from standard_labor where task = ?";
            ps = conn.prepareStatement(sql);
            String taskName = this.getTaskName();
            ps.setString(1,taskName);
            rs = ps.executeQuery();
            if(rs.next()){
                pay+=rs.getInt(1);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            DatabaseConnector.closeQuietly(rs);
            DatabaseConnector.closeQuietly(ps);
            DatabaseConnector.closeQuietly(conn);
        }
        
        return pay;
    }
    
}
