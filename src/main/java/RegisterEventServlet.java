/* RegisterEventServlet.java */
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

@WebServlet("/RegisterEventServlet")
public class RegisterEventServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String eventId = request.getParameter("event_id");
        String fullName = request.getParameter("full_name");
        String department = request.getParameter("department");
        String className = request.getParameter("class");
        String rollNumber = request.getParameter("roll_number");
        String contactNumber = request.getParameter("contact_number");
        String emailId = request.getParameter("email_id");

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");
            
            String insertSQL = "INSERT INTO event_registration (event_id, full_name, department, class, roll_number, contact_number, email_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(insertSQL);
            stmt.setInt(1, Integer.parseInt(eventId));
            stmt.setString(2, fullName);
            stmt.setString(3, department);
            stmt.setString(4, className);
            stmt.setString(5, rollNumber);
            stmt.setString(6, contactNumber);
            stmt.setString(7, emailId);
            
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                String selectEvent = "SELECT event_name, start_time, poster FROM event WHERE event_id = ?";
                PreparedStatement eventStmt = con.prepareStatement(selectEvent);
                eventStmt.setInt(1, Integer.parseInt(eventId));
                rs = eventStmt.executeQuery();
                
                if (rs.next()) {
                    String eventName = rs.getString("event_name");
                    String eventTime = rs.getString("start_time");
                    String eventPoster = rs.getString("poster");
                    
                    out.println("<script>");
                    out.println("window.opener.showTicketPopup('" + eventName + "', '" + eventTime + "', '" + fullName + "', '" + eventPoster + "');");
                    out.println("window.close();");
                    out.println("</script>");
                }
            } else {
                out.println("<h3>Registration failed</h3>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error in registration</h3>");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}