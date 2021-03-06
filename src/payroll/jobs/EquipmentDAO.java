/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import payroll.DatabaseConnector;
import payroll.tasks.NonSerializedEquipmentTask;
import payroll.tasks.SerializedEquipmentTask;

/**
 *
 * @author Casey
 */
public class EquipmentDAO {
    
    private Connection conn;
    private PreparedStatement ps;
    private Job job;
    
    public EquipmentDAO(){
        this.conn = null;
        this.ps = null;
        this.job = null;
    }
    
    public EquipmentDAO(Job inJob){
        this.conn = null;
        this.ps = null;
        this.job = inJob;
    }
    
    protected void setJob(Job inJob){
        this.job = inJob;
    }
    
    protected void addSerializedEquipmentFromSet(Set<SerializedEquipmentTask> list){
        
        String workOrderNum = job.getWorkOrderNumber();
        String techID = job.getTechID();
        String serialNumber;
        String model;
        
        if(!list.isEmpty()){
            try{
                conn = DatabaseConnector.getConnection();
                
                String sql = "insert into consumed_equip_serialized values(?,?,?,?) ";
                
                for(SerializedEquipmentTask task : list){
                    
                    serialNumber = task.getSerialNumber();
                    model = task.getModel();
                    
                    ps = conn.prepareStatement(sql);
                    
                    ps.setString(1, serialNumber);
                    ps.setString(2, model);
                    ps.setString(3, techID);
                    ps.setString(4, workOrderNum);
                    
                    ps.executeUpdate();
                }
                
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
                System.out.println(e.getSQLState());
                System.out.println("techID " + techID + " WO# " + workOrderNum);
            }
            finally{
                DatabaseConnector.closeQuietly(conn);
                DatabaseConnector.closeQuietly(ps);
            }
        }
    }
    
    protected void addNonSerializedEquipmentFromList(List<NonSerializedEquipmentTask> list){
        String workOrderNum = job.getWorkOrderNumber();
        String techID = job.getTechID();
        String model;
        
        if(!list.isEmpty()){
            try{
                conn = DatabaseConnector.getConnection();
                
                String sql = "insert into consumed_equip_nonserialized "
                            + "(model, workOrderNum, techID, quantity) "
                            + "values (?,?,?,1)"
                            + "on duplicate key update quantity = quantity + 1";

                for(NonSerializedEquipmentTask task : list){
                    model = task.getTaskDescription();
                    ps = conn.prepareStatement(sql);
                    ps.setString(1,model);
                    ps.setString(2, workOrderNum);
                    ps.setString(3, techID);
                    
                    ps.executeUpdate();
                }
                
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
                System.out.println(e.getSQLState());
            }
            finally{
                DatabaseConnector.closeQuietly(conn);
                DatabaseConnector.closeQuietly(ps);
            }
        }
    }
}
