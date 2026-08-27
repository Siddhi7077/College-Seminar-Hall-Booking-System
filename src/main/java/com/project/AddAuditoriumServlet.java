package com.project;

import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,  // 2MB before writing to disk
    maxFileSize = 1024 * 1024 * 10,       // Max file size: 10MB
    maxRequestSize = 1024 * 1024 * 50     // Max request size: 50MB
)
public class AddAuditoriumServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Step 1: Get form data
        String name = request.getParameter("auditoriumName");
        int capacity = Integer.parseInt(request.getParameter("seatCapacity"));
        Part imagePart = request.getPart("auditoriumMedia"); // Ensure this matches form input name

        // Step 2: Check for null values
        if (imagePart == null || imagePart.getSize() == 0) {
            response.getWriter().write("Error: No file uploaded.");
            return;
        }

        // Step 3: Get file details
        String fileName = System.currentTimeMillis() + "_" + imagePart.getSubmittedFileName(); // Unique filename

        // Path to save inside webapp (Project Folder)
        String uploadDirPath = getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) uploadDir.mkdir(); // Create 'uploads' folder if not exists

        String filePath = uploadDirPath + File.separator + fileName;
        imagePart.write(filePath); // Save file in 'webapp/uploads/'

        // Save relative path in database
        String relativePath = "uploads/" + fileName;

        // Step 5: Save details in database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            PreparedStatement stmt = c.prepareStatement("INSERT INTO auditorium (name, capacity, image) VALUES (?, ?, ?)");
            stmt.setString(1, name);
            stmt.setInt(2, capacity);
            stmt.setString(3, relativePath);
            stmt.executeUpdate();
            c.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect back to admin page
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('Auditorium added successfully!'); window.location='Admin1.html';</script>");
        out.close();
    }
}
