package com.project;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String action = request.getParameter("action");

            HttpSession session = request.getSession(); // Create a session

            if ("login".equals(action)) {
                if (authenticateUser(con, username, password, "admin", "aid", "username", request, response, "Admin1.html", session)) return;
                if (authenticateUser(con, username, password, "teacher", "t_id", "user_name", request, response, "Teacher1.html", session)) return;
             //   if (authenticateUser(con, username, password, "student", "s_id", "u_name", request, response, "first.html", session)) return;
                if (authenticateUser(con, username, password, "student", "s_id", "u_name", request, response, "Home.html?login=success", session)) return;

                
                // If login fails, send error message
                response.sendRedirect("first.html?error=Invalid+username+or+password");
            } else if ("signup".equals(action)) { 
                if (registerStudent(con, username, password)) {
                    session.setAttribute("username", username);
                    session.setAttribute("role", "student");
                    response.sendRedirect("first.html?success=Account+created+successfully");
                } else {
                    response.sendRedirect("first.html?error=Username+already+exists");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("first.html?error=An+unexpected+error+occurred");
        }
    }

    // Authenticate User and Set Session
    private boolean authenticateUser(Connection con, String username, String password, String table, String idColumn, String userColumn, HttpServletRequest request, HttpServletResponse response, String redirectPage, HttpSession session) throws SQLException, IOException {
        String passwordColumn = table.equals("student") ? "s_password" : "password"; 

        PreparedStatement ps = con.prepareStatement("SELECT * FROM " + table + " WHERE " + userColumn + " = ? AND " + passwordColumn + " = ?");
        ps.setString(1, username);
        ps.setString(2, password.trim()); // Trim spaces

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            session.setAttribute("username", username);
            session.setAttribute("role", table);
            
            if (table.equals("teacher")) {
                int teacherId = rs.getInt(idColumn); // Fetch t_id from DB
                session.setAttribute("t_id", teacherId);
            }
           
            response.sendRedirect(redirectPage);
            if (table.equals("student")) {
                int studentId = rs.getInt(idColumn); // Fetch s_id from DB
                session.setAttribute("s_id", studentId); // Store student ID in session
                System.out.println("Student ID stored in session: " + studentId); // Print it
            }
            return true;
        }
        return false;
    }


    // Insert New Student on Signup
    private boolean registerStudent(Connection con, String username, String password) throws SQLException {
        // Check if username already exists
        PreparedStatement checkUser = con.prepareStatement("SELECT * FROM student WHERE u_name = ?");
        checkUser.setString(1, username);
        ResultSet rs = checkUser.executeQuery();

        if (rs.next()) {
            return false; // Username already exists
        }

        // Insert new student record
        PreparedStatement ps = con.prepareStatement("INSERT INTO student (u_name, s_password) VALUES (?, ?)");
        ps.setString(1, username);
        ps.setString(2, password);
        int rowsInserted = ps.executeUpdate();
        return rowsInserted > 0;
    }
}
