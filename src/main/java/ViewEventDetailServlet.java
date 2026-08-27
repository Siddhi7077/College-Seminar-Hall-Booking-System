import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ViewEventDetailServlet")
public class ViewEventDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String eventIdParam = request.getParameter("id");
        HttpSession session = request.getSession();
        Integer studentId = (Integer) session.getAttribute("s_id");

        int eventId;
        try {
            eventId = Integer.parseInt(eventIdParam);
        } catch (NumberFormatException e) {
            response.getWriter().write("<h3>Invalid Event ID</h3>");
            return;
        }

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            String sql = "SELECT * FROM event WHERE event_id = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, eventId);
            rs = stmt.executeQuery();

            StringBuilder htmlResponse = new StringBuilder();

            if (rs.next()) {
                String status = rs.getString("status");
                String registrationType = rs.getString("registration_type");
                int aid = rs.getInt("aid");

                htmlResponse.append("<div id='mainbox'>");  // Main container
                htmlResponse.append("<h2>").append(rs.getString("event_name")).append("</h2>");
                htmlResponse.append("<p><b>Date:</b> ").append(rs.getString("event_date")).append("</p>");
                htmlResponse.append("<p><b>Time:</b> ").append(rs.getString("start_time")).append(" - ")
                            .append(rs.getString("end_time")).append("</p>");
                htmlResponse.append("<p><b>Department:</b> ").append(rs.getString("department")).append("</p>");
                htmlResponse.append("<p><b>Hosted By:</b> ").append(rs.getString("hosted_by")).append("</p>");
                htmlResponse.append("<p><b>Description:</b> ").append(rs.getString("description")).append("</p>");
                htmlResponse.append("<p><b>Cost:</b> ").append(rs.getInt("cost")).append("</p>");
                htmlResponse.append("<p><b>Status:</b> ").append(status).append("</p>");
                htmlResponse.append("<img src='").append(rs.getString("event_poster")).append("' width='300' alt='Event Poster'>");

                // Registration Condition: If event is upcoming and registration type is "registered"
                if ("Upcoming".equalsIgnoreCase(status) && "registered".equalsIgnoreCase(registrationType)) {
                    htmlResponse.append("<br><button onclick='showRegistrationForm()'>Register</button>");
                    htmlResponse.append("<script>");
                    htmlResponse.append("function showRegistrationForm() {");
                    htmlResponse.append("    var form = document.getElementById('registrationForm');");
                    htmlResponse.append("    if (form.style.display === 'none') { form.style.display = 'block'; }");
                    htmlResponse.append("    else { form.style.display = 'none'; }");
                    htmlResponse.append("}");
                    htmlResponse.append("</script>");
                    
                    htmlResponse.append("<div id='registrationForm' style='display:none;'>");
                    htmlResponse.append("<form id='regForm' method='POST' action='Event_Registration'>");
                    htmlResponse.append("<input type='hidden' name='event_id' value='" + eventId + "'>");
                    htmlResponse.append("Full Name: <input type='text' name='full_name' required><br>");
                    htmlResponse.append("Department: <input type='text' name='department' required><br>");
                    htmlResponse.append("Class: <input type='text' name='class' required><br>");
                    htmlResponse.append("Roll Number: <input type='text' name='roll_number' required><br>");
                    htmlResponse.append("Contact Number: <input type='text' name='contact_number' required><br>");
                    htmlResponse.append("Email: <input type='email' name='email_id' required><br>");
                    htmlResponse.append("<button type='submit'>Register</button>");
                    htmlResponse.append("</form>");
                    htmlResponse.append("</div>");
                }
                // Ticket Booking Condition: If event is upcoming and registration type is "ticket"
                else if ("Upcoming".equalsIgnoreCase(status) && "ticket".equalsIgnoreCase(registrationType)) {
                    htmlResponse.append("<br><button onclick=\"window.location.href='Book_Ticket.html?aid=" + aid + "'\">Book Ticket</button>");
                }

                htmlResponse.append("</div>");  // Closing mainbox div
            } else {
                htmlResponse.append("<h3>Event not found</h3>");
            }

            response.getWriter().write(htmlResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<h3>Error retrieving event details. Please try again later.</h3>");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
