import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEventServlet")
public class DeleteEventServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int eventId = Integer.parseInt(request.getParameter("event_id"));  // Fixed parameter name

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");
            
            PreparedStatement ps = con.prepareStatement("DELETE FROM event WHERE event_id=?");  // Fixed table name
            ps.setInt(1, eventId);
            
            int rowsDeleted = ps.executeUpdate();
            con.close();

            response.sendRedirect("ViewEvent.jsp");  // Redirect after deletion
        } catch (Exception e) {
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
