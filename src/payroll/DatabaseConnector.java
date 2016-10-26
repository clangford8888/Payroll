/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Casey
 */
public class DatabaseConnector {
    
    public static Connection getConnection() throws SQLException{
        Connection conn = null;
        
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll", "root", "");
        }
        catch(SQLException e){
            System.out.print(e.getMessage());
            System.out.print(e.getSQLState());
            System.out.println("Error connecting to database!");
            return null;
        }
        return conn;
    }
    
    // Take in a JDBC Connection and close while checking for nulls
    public static void closeQuietly(Connection inConnection) {
        Connection conn = inConnection;
        if(conn != null){
            try{
                conn.close();
            }
            catch(SQLException e){
                System.out.print(e.getMessage());
                System.out.print(e.getSQLState());
            }
        }
    }
    
    // Take in a JDBC Result Set and close while checking for nulls
    public static void closeQuietly(ResultSet inResultSet){
        ResultSet rs = inResultSet;
        if(rs != null){
            try{
                rs.close();
            }
            catch(SQLException e){
                System.out.print(e.getMessage());
                System.out.print(e.getSQLState());
            }
        }
    }
    
    // Take in a JDBC PreparedStatement and close while checking for nulls
    public static void closeQuietly(PreparedStatement inPreparedStatement){
        PreparedStatement ps = inPreparedStatement;
        if(ps != null){
            try{
                ps.close();
            }
            catch(SQLException e){
                System.out.print(e.getMessage());
                System.out.print(e.getSQLState());
            }
        }
    }
}