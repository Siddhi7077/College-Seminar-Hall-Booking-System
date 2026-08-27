import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AddEventServlet")
@MultipartConfig(maxFileSize = 16177215)  // Enables file upload handling
public class AddEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            // Retrieve form data
            String eventName = request.getParameter("event-name");
            String eventDate = request.getParameter("event-date");
            String startTime = request.getParameter("start-time");
            String endTime = request.getParameter("end-time");
            String department = request.getParameter("department");
            String hostedBy = request.getParameter("hosted-by");
            String description = request.getParameter("description");
            String auditoriumId = request.getParameter("auditorium");
            String costParam = request.getParameter("cost");

            // Retrieve new registration inputs
            String registrationType = request.getParameter("registration_type");
            if (registrationType == null || registrationType.trim().isEmpty()) {
                registrationType = "none";  // default value if not provided
            }
            String registrationLimitParam = request.getParameter("registration_limit");
            int registrationLimit = 0;
            if (registrationLimitParam != null && !registrationLimitParam.trim().isEmpty()) {
                registrationLimit = Integer.parseInt(registrationLimitParam);
            }

            // Get teacher ID from session
            HttpSession session = request.getSession();
            Integer teacherId = (Integer) session.getAttribute("t_id"); // Correct key

            if (teacherId == null) {
                response.sendRedirect("login.html");
                return;
            }

            // Handling file upload (Event Poster)
            Part filePart = request.getPart("event-poster");
            String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName(); // Unique filename

            // Path to save inside webapp (Project Folder)
            String uploadDirPath = getServletContext().getRealPath("/uploads");
            File uploadDir = new File(uploadDirPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir(); // Create 'uploads' folder if not exists
            }

            String filePath = uploadDirPath + File.separator + fileName;
            filePart.write(filePath); // Save file in 'webapp/uploads/'

            // Save relative path in database
            String relativePath = "uploads/" + fileName;

            // SQL Query to Insert Event with new registration columns
            String sql = "INSERT INTO event (event_name, event_date, start_time, end_time, department, hosted_by, description, event_poster, aid, t_id, cost, registration_type, registration_limit) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, eventName);
            pstmt.setString(2, eventDate);
            pstmt.setString(3, startTime);
            pstmt.setString(4, endTime);
            pstmt.setString(5, department);
            pstmt.setString(6, hostedBy);
            pstmt.setString(7, description);
            pstmt.setString(8, relativePath); // Save only relative path
            pstmt.setInt(9, Integer.parseInt(auditoriumId)); // 'aid'
            pstmt.setInt(10, teacherId); // 't_id'
            pstmt.setInt(11, Integer.parseInt(costParam));
            pstmt.setString(12, registrationType);   // New column: registration_type
            pstmt.setInt(13, registrationLimit);      // New column: registration_limit

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("Teacher1.html");
            } else {
                response.sendRedirect("error.html");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
