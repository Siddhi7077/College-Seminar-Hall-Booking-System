import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Upcoming_Event_Servlet_Images")
public class Upcoming_Event_Servlet_Images extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String type = request.getParameter("type"); // Get event type from request
        if (type == null || type.isEmpty()) {
            out.println("<p>No events found.</p>");
            return;
        }

        try {
            // Database Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            // Query to fetch upcoming events
            String upcomingQuery = "SELECT * FROM event WHERE type=? AND status='Upcoming'";
            PreparedStatement upcomingStmt = conn.prepareStatement(upcomingQuery);
            upcomingStmt.setString(1, type);
            ResultSet upcomingResult = upcomingStmt.executeQuery();

            // Display Upcoming Events
            while (upcomingResult.next()) {
                String eventPoster = upcomingResult.getString("event_poster");
                String eventName = upcomingResult.getString("event_name");
                String eventDate = upcomingResult.getString("event_date");
                String startTime = upcomingResult.getString("start_time");
                String endTime = upcomingResult.getString("end_time");
                String department = upcomingResult.getString("department");
                String hostedBy = upcomingResult.getString("hosted_by");
                String description = upcomingResult.getString("description");

                out.println("<div class='event-item'>");
                out.println("<img src='" + eventPoster + "' class='event-img'>");
                out.println("<div class='event-info'>");
                out.println("<h4>" + eventName + "</h4>");
                out.println("<p>Date: " + eventDate + "</p>");
                out.println("<p>Time: " + startTime + " - " + endTime + "</p>");
                out.println("<p>Department: " + department + "</p>");
                out.println("<p>Hosted by: " + hostedBy + "</p>");
                out.println("<p>" + description + "</p>");
                out.println("</div>");
                out.println("</div>");
            }

            // Query to fetch completed events
            String completedQuery = "SELECT * FROM event WHERE type=? AND status='Completed'";
            PreparedStatement completedStmt = conn.prepareStatement(completedQuery);
            completedStmt.setString(1, type);
            ResultSet completedResult = completedStmt.executeQuery();

            // Display Completed Events
            while (completedResult.next()) {
                String eventPoster = completedResult.getString("event_poster");
                String eventName = completedResult.getString("event_name");
                int eventId = completedResult.getInt("event_id");

                out.println("<div class='event-item'>");
                out.println("<img src='" + eventPoster + "' class='event-img'>");
                out.println("<div class='event-info'>");
                out.println("<h4>" + eventName + "</h4>");
                out.println("<button class='details-btn' onclick='showDetails(" + eventId + ")'>Details</button>");
                out.println("</div>");
                out.println("</div>");
            }

            conn.close();
        } catch (Exception e) {
            out.println("<p>Error fetching events: " + e.getMessage() + "</p>");
        }
    }
}
