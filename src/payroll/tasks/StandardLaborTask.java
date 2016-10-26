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
public class StandardLaborTask implements LaborTask{
    
    private final String advancedTaskType;
    private final String taskName;
    private final String taskDescription;
    private int payment;
    
    public StandardLaborTask(String inName, String inDescription){
        this.advancedTaskType = "Standard Labor";
        this.taskName = inName;
        this.taskDescription = inDescription;
    }
    
    // 2nd constructor to accept a payment
    public StandardLaborTask(String inName, String inDescription, int payment){
        this.advancedTaskType = "Standard Labor";
        this.payment = payment;
        this.taskName = inName;
        this.taskDescription = inDescription;
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
    
    // TODO: COMMENTS
    // TODO: Maybe move database calls and stuff to SOMEWHERE ELSE!!
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
            String name = this.getTaskName();
            ps.setString(1,name);
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
