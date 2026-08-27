package com.project;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/tydb36";
    private static final String USER = "Enter Your sql setup username";
    private static final String PASSWORD = "Enter you sql setup password";
    ResultSet rs;
    Statement s;
    // Method to establish database connection
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
        
    }

    public static void main(String[] args) {
       ResultSet rs;
       Statement s;
    	try (Connection conn = getConnection()) {
            if (conn != null) {
            	s=conn.createStatement();
    			rs=s.executeQuery("select * from teacher");
    			while(rs.next()) {
    				System.out.println("Id "+rs.getString(1)+"\nUser Name "+rs.getString(2));
                System.out.println("Database Connection Successful!");
            }
            }
    			else {
                System.out.println("Database Connection Failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
