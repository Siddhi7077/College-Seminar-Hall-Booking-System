import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/Event_Registration")
public class Event_Registration extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Get Student ID from Session
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("s_id") == null) {
                out.println("<html><body><h3>Error: Session Expired. Please login again.</h3></body></html>");
                return;
            }
            int s_id = (int) session.getAttribute("s_id");

            // Get form data from request
            String event_id = request.getParameter("event_id");
            String full_name = request.getParameter("full_name");
            String department = request.getParameter("department");
            String className = request.getParameter("class");
            String roll_number = request.getParameter("roll_number");
            String contact_number = request.getParameter("contact_number");
            String email_id = request.getParameter("email_id");

            // Connect to Database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            // Check if Student Already Registered for the Event
            String checkQuery = "SELECT COUNT(*) FROM event_registration WHERE event_id = ? AND s_id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkQuery);
            checkStmt.setInt(1, Integer.parseInt(event_id));
            checkStmt.setInt(2, s_id);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                out.println("<html><body><h3 style='color:red;'>You have already registered for this event!</h3></body></html>");
                return;
            }

            // Check Registration Limit
            String limitQuery = "SELECT registration_limit FROM event WHERE event_id = ?";
            PreparedStatement limitStmt = con.prepareStatement(limitQuery);
            limitStmt.setInt(1, Integer.parseInt(event_id));
            ResultSet limitRs = limitStmt.executeQuery();
            int registrationLimit = 0;
            if (limitRs.next()) {
                registrationLimit = limitRs.getInt("registration_limit");
            }

            // Count Current Registrations
            String countQuery = "SELECT COUNT(*) FROM event_registration WHERE event_id = ?";
            PreparedStatement countStmt = con.prepareStatement(countQuery);
            countStmt.setInt(1, Integer.parseInt(event_id));
            ResultSet countRs = countStmt.executeQuery();
            int currentRegistrations = 0;
            if (countRs.next()) {
                currentRegistrations = countRs.getInt(1);
            }

            // Check if registration limit is reached
            if (currentRegistrations >= registrationLimit) {
                out.println("<html><body><h3 style='color:red;'>Registration for this event is full!</h3></body></html>");
                return;
            }

            // Get Event Details
            String eventQuery = "SELECT event_name, event_date, event_poster FROM event WHERE event_id = ?";
            PreparedStatement eventStmt = con.prepareStatement(eventQuery);
            eventStmt.setInt(1, Integer.parseInt(event_id));
            ResultSet eventRs = eventStmt.executeQuery();
            String event_name = "";
            String event_date = "";
            String event_poster = "";
            if (eventRs.next()) {
                event_name = eventRs.getString("event_name");
                event_date = eventRs.getString("event_date");
                event_poster = eventRs.getString("event_poster");
            }

            // Insert Registration Data
            String sql = "INSERT INTO event_registration (event_id, s_id, full_name, department, class, roll_number, contact_number, email_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(event_id));
            stmt.setInt(2, s_id);
            stmt.setString(3, full_name);
            stmt.setString(4, department);
            stmt.setString(5, className);
            stmt.setString(6, roll_number);
            stmt.setString(7, contact_number);
            stmt.setString(8, email_id);
            stmt.executeUpdate();

            // Generate Ticket Popup
            out.println("<html><head><style>");
            out.println(".ticket-container { display: flex; border: 2px solid #333; width: 500px; margin: auto; padding: 10px; }");
            out.println(".ticket-left { width: 40%; }");
            out.println(".ticket-left img { width: 100%; height: auto; }");
            out.println(".ticket-right { width: 60%; padding-left: 10px; }");
            out.println("</style></head><body>");
            out.println("<div class='ticket-container'>");
            out.println("<div class='ticket-left'><img src='" + event_poster + "' alt='Event Poster'></div>");
            out.println("<div class='ticket-right'>");
            out.println("<h2>🎟 Event Ticket</h2>");
            out.println("<p><b>Event:</b> " + event_name + "</p>");
            out.println("<p><b>Date:</b> " + event_date + "</p>");
            out.println("<p><b>Name:</b> " + full_name + "</p>");
            out.println("<p><b>Booked On:</b> " + new java.util.Date() + "</p>");
            out.println("<button onclick='window.print()'>Print Ticket</button>");
            out.println("<a href='Home.html'><button>Back to Home</button></a>");
            out.println("</div></div>");
            out.println("</body></html>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<html><body><h3>Error: Registration failed.</h3></body></html>");
        }
    }
}