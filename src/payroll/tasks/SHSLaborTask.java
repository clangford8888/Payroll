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
public class SHSLaborTask implements LaborTask{
    
    private final String advancedTaskType;
    private final String taskName;
    private final String taskDescription;
    private int payment;
    
    public SHSLaborTask(String inName, String inDescription){
        this.advancedTaskType = "SHS Labor";
        this.taskName = inName;
        this.taskDescription = inDescription;
    }
    
    public SHSLaborTask(String inName, String inDescription, int payment){
        this.advancedTaskType = "SHS Labor";
        this.taskName = inName;
        this.taskDescription = inDescription;
        this.payment = payment;
    }
    
    @Override
    public String getAdvancedTaskType(){
        return advancedTaskType;
    }
    
    @Override
    public int getPayment(){
        return this.payment;
    }
    
    @Override
    public String getTaskType(){
        return LaborTask.TASK_TYPE;
    }
    
    @Override
    public String getTaskName(){
        return this.taskName;
    }
    
    @Override
    public String getTaskDescription(){
        return this.taskDescription;
    }
    
    @Override
    public void setPayment(int inPayment){
        this.payment = inPayment;
    }
    
    
    // NEED TO CHANGE THIS TO LOOKUP PAYMENT
    @Override
    public int lookupLaborPayment(){
        int pay = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select payment from shs_labor where task = ?";
            ps = conn.prepareStatement(sql);
            String name = this.getTaskName();
            ps.setString(1,name);
            rs = ps.executeQuery();
            if(rs.next()){
                pay += rs.getInt(1);
                this.setPayment(pay);
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
