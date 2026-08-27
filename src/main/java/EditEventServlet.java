import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/EditEventServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class EditEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Debugging: print received parameters
            System.out.println("Received Parameters:");
            request.getParameterMap().forEach((key, value) -> 
                System.out.println(key + " = " + (value.length > 0 ? value[0] : "NULL"))
            );

            // Check event_id
            String eventIdStr = request.getParameter("event_id");
            if (eventIdStr == null || eventIdStr.isEmpty()) {
                response.getWriter().write("Error: event_id is missing.");
                return;
            }
            int eventId = Integer.parseInt(eventIdStr);

            // Get form parameters
            String newName = request.getParameter("event_name");
            String newDate = request.getParameter("event_date");
            String newStartTime = request.getParameter("start_time");
            String newEndTime = request.getParameter("end_time");
            String department = request.getParameter("department");
            String hostedBy = request.getParameter("hosted_by");
            String description = request.getParameter("description");
            // These may be hidden fields in the form:
            int aid = parseInteger(request.getParameter("aid"), 0);
            int t_id = parseInteger(request.getParameter("t_id"), 0);
            int cost = parseInteger(request.getParameter("cost"), 0);
            String status = request.getParameter("status");

            // New fields for registration options
            String registrationType = request.getParameter("registration_type");
            if (registrationType == null || registrationType.trim().isEmpty()) {
                registrationType = "none";
            }
            String registrationLimitParam = request.getParameter("registration_limit");
            int registrationLimit = 0;
            if (registrationLimitParam != null && !registrationLimitParam.trim().isEmpty()) {
                registrationLimit = Integer.parseInt(registrationLimitParam);
            }

            // Handle file upload (Event Poster)
            Part filePart = request.getPart("event-poster");
            String filePath = null;
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
                filePath = "uploads/" + fileName;

                // Save file into the uploads directory in your webapp
                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                File file = new File(uploadPath + File.separator + fileName);
                try (FileOutputStream fos = new FileOutputStream(file);
                     InputStream is = filePart.getInputStream()) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
            }

            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            String query;
            if (filePath != null) {
                // Update including new event_poster
                query = "UPDATE event SET event_name=?, event_date=?, start_time=?, end_time=?, department=?, hosted_by=?, description=?, event_poster=?, aid=?, t_id=?, cost=?, status=?, registration_type=?, registration_limit=? WHERE event_id=?";
            } else {
                query = "UPDATE event SET event_name=?, event_date=?, start_time=?, end_time=?, department=?, hosted_by=?, description=?, aid=?, t_id=?, cost=?, status=?, registration_type=?, registration_limit=? WHERE event_id=?";
            }

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newName);
            ps.setString(2, newDate);
            ps.setString(3, newStartTime);
            ps.setString(4, newEndTime);
            ps.setString(5, department);
            ps.setString(6, hostedBy);
            ps.setString(7, description);

            int idx = 8;
            if (filePath != null) {
                ps.setString(idx++, filePath);
            }

            ps.setInt(idx++, aid);
            ps.setInt(idx++, t_id);
            ps.setInt(idx++, cost);
            ps.setString(idx++, status);
            ps.setString(idx++, registrationType);
            ps.setInt(idx++, registrationLimit);
            ps.setInt(idx, eventId);

            int rowsUpdated = ps.executeUpdate();
            response.setContentType("text/html");
            if (rowsUpdated > 0) {
                response.getWriter().write("<script>alert('Event updated successfully!'); window.location='Teacher1.html';</script>");
            } else {
                response.getWriter().write("<script>alert('Error updating event.'); window.location='error.html';</script>");
            }
            con.close();
        } catch (Exception e) {
            response.getWriter().write("Error: " + e.getMessage());
        }
    }

    // Helper method to parse integers safely
    private int parseInteger(String value, int defaultValue) {
        try {
            return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
