import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Fetch_Events_In_Events")
public class Fetch_Events_In_Events extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String type = request.getParameter("type");
        String category = request.getParameter("category");
        String department = request.getParameter("department");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            // Build SQL query dynamically
            String sql = "SELECT * FROM event WHERE 1=1";
            if (type != null) sql += " AND type = ?";
            if (category != null) sql += " AND category = ?";
            
            // Department filtering logic
            if (department != null) {
                if (department.equalsIgnoreCase("Junior")) {
                    sql += " AND (department = 'Science' OR department = 'Commerce' OR department = 'Arts' OR department = 'All Department')";
                } else if (department.equalsIgnoreCase("Degree")) {
                    sql += " AND (department != 'Science' AND department != 'Commerce' AND department != 'Arts' OR department = 'All Department')";
                }
            }

            sql += " ORDER BY event_date ASC";

            PreparedStatement ps = con.prepareStatement(sql);
            int index = 1;
            if (type != null) ps.setString(index++, type);
            if (category != null) ps.setString(index++, category);

            ResultSet rs = ps.executeQuery();

            int count = 0;
            while (rs.next()) {
                String eventId = rs.getString("event_id");
                String eventName = rs.getString("event_name");
                String eventDate = rs.getString("event_date");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String eventDepartment = rs.getString("department");
                String hostedBy = rs.getString("hosted_by");
                String description = rs.getString("description");
                String eventPoster = rs.getString("event_poster");
                String status = rs.getString("status");
                String eventCategory = rs.getString("category");

                String alignment = (count % 2 == 0) ? "right-layout" : "left-layout";

                out.println("<div class='event-box " + alignment + "'>");
                out.println("<div class='event-details'>");
                out.println("<h3>" + eventName + "</h3>");
                out.println("<p><strong>Date:</strong> " + eventDate + " " + startTime + " - " + endTime + "</p>");
                out.println("<p><strong>Department:</strong> " + eventDepartment + "</p>");
                out.println("<p><strong>Hosted By:</strong> " + hostedBy + "</p>");
                out.println("<p><strong>Description:</strong> " + description + "</p>");
                out.println("<p><strong>Category:</strong> " + eventCategory + "</p>");
                if (status.equalsIgnoreCase("Completed")) {
                    out.println("<a href='EventDetail.html?id=" + eventId + "' class='btn btn-primary'>Details</a>");
                }
                out.println("</div>");
                out.println("<div class='event-image'><img src='" + eventPoster + "' alt='" + eventName + "' class='zoomable'></div>");
                out.println("</div>");

                count++;
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error fetching events.</p>");
        }
    }
}
