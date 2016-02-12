/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Casey
 */
public class EquipmentDAO {
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private Job job;
    
    public EquipmentDAO(Job inJob){
        this.conn = null;
        this.ps = null;
        this.rs = null;
        this.job = inJob;
    }
    
    protected void addSerializedEquipmentFromList(List<SerializedEquipmentTask> list){
        
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
                    
                    int result = ps.executeUpdate();
                    
                    if(result > 0){
                        System.out.println("Added: " + model);
                    }
                }
                
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
                System.out.println(e.getSQLState());
            }
            finally{
                DatabaseConnector.closeQuietly(conn);
                DatabaseConnector.closeQuietly(ps);
                DatabaseConnector.closeQuietly(rs);
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
                    
                    int result  = ps.executeUpdate();
                    
                    if(result > 1){
                        System.out.println("Added: 1 " + model);
                    }
                }
                
            }
            catch(SQLException e){
                System.out.println(e.getMessage());
                System.out.println(e.getSQLState());
            }
            finally{
                DatabaseConnector.closeQuietly(conn);
                DatabaseConnector.closeQuietly(ps);
                DatabaseConnector.closeQuietly(rs);
            }
        }
    }
    
    
    // Can remove this method. Just here for test purposes
    public void testInsertUpdate(){
        try{
            conn = DatabaseConnector.getConnection();
            
            String sql = "insert into consumed_equip_nonserialized (model,workOrderNum,techID,quantity) "
                            + "values('Smart Bar& DN006087', '1002731688633011', 'Corey.Gomez1', 1) "
                            + "on duplicate key update quantity = quantity + 1";
            ps = conn.prepareStatement(sql);
            
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
            
        }
        finally{
            DatabaseConnector.closeQuietly(conn);
                DatabaseConnector.closeQuietly(ps);
                DatabaseConnector.closeQuietly(rs);
        }
        
    }
}
