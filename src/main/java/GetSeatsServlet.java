import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/GetSeatsServlet")
public class GetSeatsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        String aidParam = request.getParameter("aid");
        if (aidParam == null || aidParam.isEmpty()) {
            response.getWriter().write("<h3>Error: Missing Event ID</h3>");
            return;
        }

        int aid = Integer.parseInt(aidParam);
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int capacity = 0;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            // Step 1: Get auditorium capacity
            String sql1 = "SELECT capacity FROM auditorium WHERE id = ?";
            pstmt = con.prepareStatement(sql1);
            pstmt.setInt(1, aid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                capacity = rs.getInt("capacity");
            }

            // Step 2: Get booked seats
            String sql2 = "SELECT seat_number FROM ticket_booking WHERE event_id = ?";
            pstmt = con.prepareStatement(sql2);
            pstmt.setInt(1, aid);
            rs = pstmt.executeQuery();
            
            boolean[] bookedSeats = new boolean[capacity + 1]; // 1-based index
            while (rs.next()) {
                bookedSeats[rs.getInt("seat_number")] = true;
            }

            // Step 3: Generate seat table
            StringBuilder html = new StringBuilder("<table border='1' cellspacing='5' cellpadding='10'>");
            int cols = 10; // Number of columns per row
            int rows = (capacity + cols - 1) / cols; // Calculate rows needed

            for (int i = 0, seatNum = 1; i < rows; i++) {
                html.append("<tr>");
                for (int j = 0; j < cols && seatNum <= capacity; j++, seatNum++) {
                    if (bookedSeats[seatNum]) {
                        html.append("<td style='background-color:red; color:white;'>")
                            .append(seatNum)
                            .append("</td>");
                    } else {
                        html.append("<td onclick='openBookingPopup(")
                            .append(seatNum)
                            .append(")' style='cursor:pointer; background-color:lightgray;'>")
                            .append(seatNum)
                            .append("</td>");
                    }
                }
                html.append("</tr>");
            }
            html.append("</table>");

            response.getWriter().write(html.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<h3>Error fetching seat data</h3>");
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}
