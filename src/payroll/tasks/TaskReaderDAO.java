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
import java.util.ArrayList;
import java.util.List;
import payroll.DatabaseConnector;



/**
 *
 * @author Casey
 */
public class TaskReaderDAO {
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public TaskReaderDAO(){
        conn = null;
        ps = null;
        rs = null;
    }
    
    
    public List<Task> getNonSerializedTable(){
        
        List<Task> nonSerialized = new ArrayList<>();
        
        try{
            conn = DatabaseConnector.getConnection();
            String sql = "select * from nonserialized_equipment";
            rs = ps.executeQuery(sql);
            
            String taskName;
            String taskDescription;
            Task newTask;
            
            while(rs.next()){
                taskName = rs.getString("task");
                taskDescription = rs.getString("taskDescription");
                newTask = new NonSerializedEquipmentTask(taskName,taskDescription);
                nonSerialized.add(newTask);
            }
        }
        catch (SQLException e){
            System.out.println(e.getSQLState());
        }
        finally{
            DatabaseConnector.closeQuietly(conn);
            DatabaseConnector.closeQuietly(ps);
            DatabaseConnector.closeQuietly(rs);
        }
        return nonSerialized;
    }
}
