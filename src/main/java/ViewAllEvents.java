import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;



@WebServlet("/ViewAllEvents")
public class ViewAllEvents extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder eventListHtml = new StringBuilder();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            // Fetch Top 10 Upcoming Events
            String eventQuery = "SELECT event_id, event_name, event_date, event_poster, status FROM event ORDER BY event_date ASC LIMIT 10";
            PreparedStatement eventPs = con.prepareStatement(eventQuery);
            ResultSet eventRs = eventPs.executeQuery();

            while (eventRs.next()) {
                String id = eventRs.getString("event_id");
                String name = eventRs.getString("event_name");
                String date = eventRs.getString("event_date");
                String image = eventRs.getString("event_poster");
                String status = eventRs.getString("status");

                // Create Event List Item (Flexbox Layout)
                eventListHtml.append("<div class='event-item'>")
                        .append("<img src='").append(image).append("' alt='").append(name).append("' class='event-img'>")
                        .append("<div class='event-details'>")
                        .append("<h5>").append(name).append("</h5>")
                        .append("<p><strong>Date:</strong> ").append(date).append("</p>")
                        .append("<p><strong>Status:</strong> ").append(status).append("</p>")
                        .append("<a href='EventDetail.html?id=").append(id)
                        .append("' class='btn'>View Details</a>")
                        .append("</div>")
                        .append("</div>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return Event List HTML
        response.setContentType("text/html");
        response.getWriter().write(eventListHtml.toString());
    }
}
