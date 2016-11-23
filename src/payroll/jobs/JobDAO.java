/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.jobs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import payroll.DatabaseConnector;

/**
 *
 * @author Casey
 */
public class JobDAO {
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private final int MYSQL_DUPLICATE_PK = 1062;
    
    public JobDAO(){
        conn = null;
        ps = null;
        rs = null;
    }
    
    /**
     * Returns a list of jobs completed by a technician on a given day.
     * 
     * @param inTechID ID of the technician whose jobs are being retrieved
     * @param inDay specifies the date of the jobs being retrieved
     * @return jobList list of all jobs completed
     */
    public List<Job> getJobsByDate(String inTechID, Date inDay){
        
        List<Job> jobList = new ArrayList<>();

        try{
            conn = DatabaseConnector.getConnection();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println(e.getSQLState());
        }
        finally{
            DatabaseConnector.closeQuietlyAll(conn, ps, rs);
        }
        
        return jobList;
    }
    
    /**
     * Returns a list of jobs completed by a technician for a specified
     * date range.
     * 
     * @param inTechID ID of the technician whose jobs are being retrieved
     * @param start specifies the start date of the date range
     * @param end specifies the end date of the date range
     * @return jobList list of all jobs completed
     */
    public List<Job> getJobsByDate(String inTechID, Date start, Date end){
        List<Job> jobList = new ArrayList<>();
        
        return jobList;
    }
    
    /**
     * Adds a job into the database. Returns false if unable to add job.
     * @param inJob job to be added to database.
     * @return jobAdded
     */
    public boolean addJob(Job inJob){
        boolean jobAdded = false;
        
        try{
            conn = DatabaseConnector.getConnection();
            String workOrderNum = inJob.getWorkOrderNumber();
            String accountNum = inJob.getAccountNumber();
            Date date = inJob.getDate();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            String designation = inJob.getDesignation();
            String techID = inJob.getTechID();
            String customerName = inJob.getCustomerName();
            int payment = inJob.getPayment();
            String sql = "insert into job values(?,?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, workOrderNum);
            ps.setString(2, accountNum);
            ps.setDate(3, sqlDate);
            ps.setString(4, designation);
            ps.setString(5, techID);
            ps.setString(6, customerName);
            ps.setInt(7, payment);
            int update = ps.executeUpdate();
            
            if(update > 0){
                jobAdded = true;
            }
        }
        catch(SQLException e){
            if(e.getErrorCode() == MYSQL_DUPLICATE_PK){
                System.out.println("Tried to add duplicate job.");
            }
            else{
                System.out.println("Error Message: " + e.getMessage());
                System.out.println("MYSQL State: " + e.getSQLState());
                System.out.println("Error Code: " + e.getErrorCode());
            }
        }
        finally{
            DatabaseConnector.closeQuietlyAll(conn, ps, rs);
        }
        
        return jobAdded;
    }
    
    /**
     * Deletes a job from the database. Returns false if job was not deleted.
     * 
     * @param inJob Job to be deleted from database.
     * @return jobDeleted
     */
    public boolean deleteJob(Job inJob){
        boolean jobDeleted = false;
        
        try{
            conn = DatabaseConnector.getConnection();
            String workOrderNum = inJob.getWorkOrderNumber();
            String sql = "delete from job where workOrderNum = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, workOrderNum);
            int update = ps.executeUpdate();
            
            if(update > 0){
                jobDeleted = true;
            }
        }
        catch(SQLException e){
            System.out.println("Error Message: " + e.getMessage());
            System.out.println("MYSQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
        }
        finally{
            DatabaseConnector.closeQuietlyAll(conn, ps, rs);
        }
        
        return jobDeleted;
    }   
}
