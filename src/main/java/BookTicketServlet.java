import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/BookTicketServlet")
public class BookTicketServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        int eventId = Integer.parseInt(request.getParameter("event_id"));
        int seatNumber = Integer.parseInt(request.getParameter("seat_number"));
        String fullName = request.getParameter("full_name");
        String department = request.getParameter("department");
        String className = request.getParameter("class");
        String rollNumber = request.getParameter("roll_number");
        String contactNumber = request.getParameter("contact_number");
        String emailId = request.getParameter("email_id");

        HttpSession session = request.getSession();
        Integer studentId = (Integer) session.getAttribute("s_id");

        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232")) {
            PreparedStatement stmt = con.prepareStatement(
                "INSERT INTO ticket_booking (event_id, s_id, seat_number, full_name, department, class, roll_number, contact_number, email_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            stmt.setInt(1, eventId);
            stmt.setInt(2, studentId);
            stmt.setInt(3, seatNumber);
            stmt.setString(4, fullName);
            stmt.setString(5, department);
            stmt.setString(6, className);
            stmt.setString(7, rollNumber);
            stmt.setString(8, contactNumber);
            stmt.setString(9, emailId);
            
            stmt.executeUpdate();
            response.getWriter().write("<h3>Ticket booked successfully!</h3>");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<h3>Error booking ticket</h3>");
        }
    }
}
