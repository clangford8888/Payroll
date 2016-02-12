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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Casey
 */
public class ServiceCallDAO {
    
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private Job job;
    private Job warrantyJob;
    
    public ServiceCallDAO(Job inJob){
        this.conn = null;
        this.ps = null;
        this.rs = null;
        this.job = inJob;
    }
    
    public void setJob(Job inJob){
        this.job = inJob;
    }
    
    public Job getJob(){
        return this.job;
    }
    
    private void setWarrantyJob(Job inJob){
        this.warrantyJob = inJob;
    }
    
    public Job getWarrantyJob(){
        return this.warrantyJob;
    }
    
    // TODO ******************* COMMENT CODE ****************************
    
    protected boolean checkForWarranty(){

        boolean isWarranty = false;
        Date woDate = job.getDate();
        String accountNum = job.getAccountNumber();
        
        // Convert dates to sql dates for query
        java.sql.Date previousDaySQL = getPriorDay(woDate);
        java.sql.Date lastWarrantyDaySQL = getSixtyDaysPrior(woDate);
        
        try{
            conn = DatabaseConnector.getConnection();
            
            String sql = "select exists (select 1 "
                    + "from job "
                    + "where accountNum = ? "
                    + "and date between ? and ? )";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, accountNum);
            ps.setDate(2, lastWarrantyDaySQL);
            ps.setDate(3, previousDaySQL);
            
            rs = ps.executeQuery();
            int result = 0;
            
            if(rs.next()){
                result = rs.getInt(1);
            }
            if(result > 0){
                return true;
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
        
        return isWarranty;
    }
    
    // TODO: Comments
    // TODO: Test this method when there are multiple techs out within 60 days
    protected boolean isCurrentTechWarranty(){
        
        String techID = job.getTechID();
        String accountNum = job.getAccountNumber();
        Date date = job.getDate();
        
        java.sql.Date sixtyDaysPrior = getSixtyDaysPrior(date);
        java.sql.Date priorDay = getPriorDay(date);
        
        String warrantyTechID = "";
        
        try{
            conn = DatabaseConnector.getConnection();
            
            String sql = "select techID, date from job "
                    + "where accountNum = ? "
                    + "and date between ? and ? "
                    + "order by date desc";
            
            // TODO: Have to handle more than one tech out. Order by? Need index to do that?
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, accountNum);
            ps.setDate(2, sixtyDaysPrior);
            ps.setDate(3, priorDay);
            
            rs = ps.executeQuery();
            
            // IF warranty was found, set techID from first result
            if(rs.next()){
                warrantyTechID = rs.getString("techID");
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

        return warrantyTechID.equals(techID);
    }
    
    // Given a normal Date object, use gregorian calendar to find the 60th day
    // before the work order. Return a java.sql.Date object for use with database
    private java.sql.Date getSixtyDaysPrior(Date date){
        
        // Create new instance of a gregorian calendar for calendar arithmatic
        Calendar calendar = new GregorianCalendar();
        // Set the calendar's date to the date of work order being checked
        calendar.setTime(date);
        // Subtract 60 days from the calendar
        calendar.add(Calendar.DATE, -60);
        
        Date sixtyDaysPrior = calendar.getTime();
        
        return new java.sql.Date(sixtyDaysPrior.getTime());
    
    }
    
    // Todo: Comment
    private java.sql.Date getPriorDay(Date date){
        
        // Create new instance of a gregorian calendar for calendar arithmatic
        Calendar calendar = new GregorianCalendar();
        // Set the calendar's date to the date of work order being checked
        calendar.setTime(date);
        // Subtract 1 day from the calendar
        calendar.add(Calendar.DATE, -1);
        
        Date priorDay = calendar.getTime();
        
        return new java.sql.Date(priorDay.getTime());
    }
    
    // Todo: Comment
    protected boolean addChargeback(){
        
        String warrantyWorkOrderNum;
        String techID;
        
        String customerName = job.getCustomerName();
        int chargebackAmount = 40;
        Date chargebackDate = job.getDate();
        
        String[] queryResult = getWarrantyInfo();
        
        warrantyWorkOrderNum = queryResult[0];
        techID = queryResult[1];
        
        java.sql.Date chargebackDateSQL = new java.sql.Date(chargebackDate.getTime());
        
        try{
            conn = DatabaseConnector.getConnection();
            
            String sql = "insert into chargeback "
                    + "values (?,?,?,?,?)";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, techID);
            ps.setString(2, warrantyWorkOrderNum);
            ps.setString(3, customerName);
            ps.setInt(4, chargebackAmount);
            ps.setDate(5, chargebackDateSQL);
            
            int added = ps.executeUpdate();
            
            if(added > 0){
                return true;
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
        
        return false;
    }
    
    // Todo: Comments
    private String[] getWarrantyInfo(){
        
        String workOrderNum = "";
        String techID = "";
        
        String queryResult[] = new String[2];
        
        String accountNum = job.getAccountNumber();
        
        Date date = job.getDate();
        
        java.sql.Date dateSQL = new java.sql.Date(date.getTime());
        
        try{
            conn = DatabaseConnector.getConnection();
            
            String sql = "select workOrderNum, techID, date from job "
                    + "where accountNum = ? and "
                    + "date < ? "
                    + "order by date desc";
            
            ps = conn.prepareStatement(sql);
            
            ps.setString(1, accountNum);
            ps.setDate(2, dateSQL);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                workOrderNum = rs.getString("workOrderNum");
                techID = rs.getString("techID");
                queryResult[0] = workOrderNum;
                queryResult[1] = techID;
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
        
        return queryResult;
    }
}
