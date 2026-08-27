import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/EditEventTeacherServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class EditEventTeacherServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("event_id"));

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Database Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            // Fetch existing event details
            String selectQuery = "SELECT * FROM event WHERE event_id=?";
            ps = con.prepareStatement(selectQuery);
            ps.setInt(1, eventId);
            rs = ps.executeQuery();

            String eventName = null, eventDate = null, startTime = null, endTime = null;
            String department = null, hostedBy = null, description = null;
            String status = null, eventType = null, eventCategory = null;
            int auditorium = 0, teacherId = 0, cost = 0;

            if (rs.next()) {
                eventName = rs.getString("event_name");
                eventDate = rs.getString("event_date");
                startTime = rs.getString("start_time");
                endTime = rs.getString("end_time");
                department = rs.getString("department");
                hostedBy = rs.getString("hosted_by");
                description = rs.getString("description");
                auditorium = rs.getInt("aid");
                teacherId = rs.getInt("t_id");
                cost = rs.getInt("cost");
                status = rs.getString("status");
                eventType = rs.getString("type");
                eventCategory = rs.getString("category");
            }
            rs.close();
            ps.close();

            // Update fields if new values exist
            eventName = getUpdatedValue(request.getParameter("event_name"), eventName);
            eventDate = getUpdatedValue(request.getParameter("event_date"), eventDate);
            startTime = getUpdatedValue(request.getParameter("start_time"), startTime);
            endTime = getUpdatedValue(request.getParameter("end_time"), endTime);
            department = getUpdatedValue(request.getParameter("department"), department);
            hostedBy = getUpdatedValue(request.getParameter("hosted_by"), hostedBy);
            description = getUpdatedValue(request.getParameter("description"), description);
            eventType = getUpdatedValue(request.getParameter("event_type"), eventType);
            eventCategory = getUpdatedValue(request.getParameter("event_category"), eventCategory);
            status = getUpdatedValue(request.getParameter("status"), status);

            auditorium = parseIntOrDefault(request.getParameter("auditorium"), auditorium);
            teacherId = parseIntOrDefault(request.getParameter("t_id"), teacherId);
            cost = parseIntOrDefault(request.getParameter("cost"), cost);

            // Handle image uploads only if event is marked "Completed"
            if ("Completed".equals(status)) {
                String uploadDirPath = getServletContext().getRealPath("/uploads");
                File uploadDir = new File(uploadDirPath);
                if (!uploadDir.exists()) uploadDir.mkdirs(); // Create directory if missing

                for (int i = 1; i <= 5; i++) {
                    Part filePart = request.getPart("event_image" + i);
                    if (filePart != null && filePart.getSize() > 0) {
                        String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
                        String filePath = uploadDirPath + File.separator + fileName;
                        filePart.write(filePath); // Save file to uploads folder
                        String relativePath = "uploads/" + fileName;

                        String imageDescription = request.getParameter("description" + i);
                        ps = con.prepareStatement("INSERT INTO event_photos (event_id, photo_url, description) VALUES (?, ?, ?)");
                        ps.setInt(1, eventId);
                        ps.setString(2, relativePath);
                        ps.setString(3, imageDescription);
                        ps.executeUpdate();
                        ps.close();
                    }
                }
            }

            // Update event details in the database
            String updateQuery = "UPDATE event SET event_name=?, event_date=?, start_time=?, end_time=?, department=?, hosted_by=?, description=?, aid=?, t_id=?, cost=?, status=?, type=?, category=? WHERE event_id=?";
            ps = con.prepareStatement(updateQuery);
            ps.setString(1, eventName);
            ps.setString(2, eventDate);
            ps.setString(3, startTime);
            ps.setString(4, endTime);
            ps.setString(5, department);
            ps.setString(6, hostedBy);
            ps.setString(7, description);
            ps.setInt(8, auditorium);
            ps.setInt(9, teacherId);
            ps.setInt(10, cost);
            ps.setString(11, status);
            ps.setString(12, eventType);
            ps.setString(13, eventCategory);
            ps.setInt(14, eventId);
            ps.executeUpdate();
            ps.close();

            response.sendRedirect("Teacher1.html?message=Event Updated Successfully");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while updating the event.");
            request.getRequestDispatcher("edit_event.jsp").forward(request, response);
        } finally {
            closeResources(rs, ps, con);
        }
    }

    // Helper method to check for updated values
    private String getUpdatedValue(String newValue, String existingValue) {
        return (newValue != null && !newValue.trim().isEmpty()) ? newValue : existingValue;
    }

    // Helper method to parse integers with default values
    private int parseIntOrDefault(String value, int defaultValue) {
        try {
            return (value != null) ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // Helper method to close DB resources
    private void closeResources(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
