package com.project;
//import com.project.Teacher;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

@WebServlet("/TeacherServlet")
public class TeacherServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            deleteTeacher(request, response);
        } else if ("edit".equals(action)) {
            showEditForm(request, response);
        } else {
            listTeachers(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        updateTeacher(request, response);
    }

    private void listTeachers(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        ArrayList<Teacher> teacherList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "password");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM teacher");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getInt("id"));
                teacher.setUsername(rs.getString("user_name"));
                teacherList.add(teacher);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("teacherList", teacherList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_dashboard.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteTeacher(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "password");
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM teacher WHERE id=?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("TeacherServlet");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Teacher teacher = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "password");
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM teacher WHERE id=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                teacher = new Teacher();
                teacher.setId(rs.getInt("id"));
                teacher.setUsername(rs.getString("user_name"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("teacher", teacher);
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_dashboard.jsp");
        dispatcher.forward(request, response);
    }

    private void updateTeacher(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "password");
            PreparedStatement stmt = conn.prepareStatement("UPDATE teacher SET user_name=?, password=? WHERE id=?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("TeacherServlet");
    }
}
