package com.project;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddTeacherServlet extends HttpServlet {
    
    // Method to establish database connection
    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure MySQL driver is loaded
        String URL = "jdbc:mysql://localhost:3306/tydb36"; // Change DB name if needed
        String USER = "ty36";
        String PASSWORD = "sid3232";
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get teacher details from form
        String username = request.getParameter("teacherUsername");
        String password = request.getParameter("teacherPassword");
           System.out.println("User Name "+username);
        // Retrieve admin_id from session
        HttpSession session = request.getSession(false);
        Integer adminId = (session != null) ? (Integer) session.getAttribute("user_id") : null;
         System.out.println("Admin id= "+adminId);
        if (adminId == null) {
            response.sendRedirect("login.html?error=Session Expired. Login again.");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            conn = getConnection(); // Establish connection

            // Insert into teacher table with admin_id
            String sql = "INSERT INTO teacher (user_name, password, aid) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, adminId); // Store admin_id in teacher table

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Teacher added successfully by Admin ID: " + adminId);
                out.println("<script>");
                out.println("alert('Teacher added successfully!');");
                out.println("window.location.href='Admin1.html';"); // Redirect to admin.jsp after alert
                out.println("</script>");
                
            } else {
                System.out.println("Failed to add teacher.");
                out.println("<script>");
                out.println("alert('Something went wrong!');");
                out.println("window.location.href='Admin1.html';");
                out.println("</script>");
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

      //  response.sendRedirect("admin.jsp"); // Redirect to admin panel after insertion
    }
}
