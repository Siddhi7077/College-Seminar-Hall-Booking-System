import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UserViewServlet")
public class UserViewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuilder auditoriumSliderHtml = new StringBuilder();
        StringBuilder upcomingEventsHtml = new StringBuilder();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            // **Fetch Auditorium Images**
            String audQuery = "SELECT image FROM auditorium";
            PreparedStatement audPs = con.prepareStatement(audQuery);
            ResultSet audRs = audPs.executeQuery();

            boolean first = true;
            auditoriumSliderHtml.append("<div id='auditoriumCarousel' class='carousel slide' data-bs-ride='carousel'><div class='carousel-inner'>");

            while (audRs.next()) {
                String imagePath = audRs.getString("image");

                auditoriumSliderHtml.append("<div class='carousel-item")
                        .append(first ? " active'>" : "'>")
                        .append("<img src='").append(imagePath)
                        .append("' class='d-block w-100' alt='Auditorium Image'></div>");

                first = false;
            }

            auditoriumSliderHtml.append("</div></div>");

            // **Fetch Upcoming Events (Only Top 3)**
            String eventQuery = "SELECT event_id, event_name, event_poster FROM event WHERE status = 'Upcoming' ORDER BY event_date ASC LIMIT 3";
            PreparedStatement eventPs = con.prepareStatement(eventQuery);
            ResultSet eventRs = eventPs.executeQuery();

            upcomingEventsHtml.append("<div class='carousel-container'>");

            while (eventRs.next()) {
                String id = eventRs.getString("event_id");
                String name = eventRs.getString("event_name");
                String image = eventRs.getString("event_poster");

                upcomingEventsHtml.append("<div class='carousel-img'>")
                        .append("<img src='").append(image).append("' alt='").append(name).append("'>")
                        .append("<div class='event-info'><h5>").append(name).append("</h5>")
                        .append("<a href='EventDetail.html?id=").append(id)
                        .append("' class='btn btn-primary'>Details</a></div>")
                        .append("</div>");
            }

            upcomingEventsHtml.append("</div>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // **Send Response**
        response.setContentType("text/html");
        response.getWriter().write(auditoriumSliderHtml.toString() + "||" + upcomingEventsHtml.toString());
    }
}
