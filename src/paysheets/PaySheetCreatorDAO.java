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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import payroll.DatabaseConnector;
import payroll.jobs.Job;

/**
 *
 * @author Casey
 */
public class PaySheetCreatorDAO {
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    public PaySheetCreatorDAO(){
        conn = null;
        ps = null;
        rs = null;
    }
    
    // Given a techID, a start date and an end date, return all jobs
    // Completed by the tech during that time frame
    public List<Job> getJobsByTech(String tech, Date start, Date end){
        
        List<Job> jobList = new ArrayList<>();
        
        try{
            conn = DatabaseConnector.getConnection();
            // Set the string version of the query to be used
            String sql = "select * from job "
                        + "where techID = ? "
                        + "and (date between ? and ?)"
                        + "order by date";
            // Convert the input Dates to SQL Date format
            long startTimeAsLong = start.getTime();
            java.sql.Date sqlStart = new java.sql.Date(startTimeAsLong);
            long endTimeAsLong = end.getTime();
            java.sql.Date sqlEnd = new java.sql.Date(endTimeAsLong);
            
            ps = conn.prepareStatement(sql);     
            ps.setString(1, tech);
            ps.setDate(2, sqlStart);
            ps.setDate(3, sqlEnd);
            rs = ps.executeQuery();
            
            String workOrder;
            String accountNumber;
            Date woDate;
            String designation;
            String customer;
            int payment;
            
            
            
            

            while(rs.next()){
                
                workOrder = rs.getString("workOrderNum");
                accountNumber = rs.getString("accountNum");
                woDate = rs.getDate("date");
                designation = rs.getString("designation");
                customer = rs.getString("customerName");
                payment = rs.getInt("payment");
                
                System.out.println("WO Num " + workOrder);
                System.out.println("Acc Num " + accountNumber);
                System.out.println("Date " + woDate);
                System.out.println("Designation " + designation);
                System.out.println("Customer " + customer);
                System.out.println("Payment " + payment);
            }
  
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL state: " + e.getSQLState());
            System.out.println("Error code: " + e.getErrorCode());
        }
        finally{
            DatabaseConnector.closeQuietly(conn);
            DatabaseConnector.closeQuietly(ps);
            DatabaseConnector.closeQuietly(rs);
        }
        
        return jobList;
    }
    
    public List<Job> getJobsByWeek(){
        List<Job> jobList = new ArrayList<>();
        
        return jobList;
    }
    
}
