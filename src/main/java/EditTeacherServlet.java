import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EditTeacherServlet")
public class EditTeacherServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int t_id = Integer.parseInt(request.getParameter("t_id"));
        String user_name = request.getParameter("user_name");
        String password = request.getParameter("password");
        int aid = Integer.parseInt(request.getParameter("aid")); // Get aid from session

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");
            PreparedStatement ps = con.prepareStatement("UPDATE teacher SET user_name=?, password=?, aid=? WHERE t_id=?");
            ps.setString(1, user_name);
            ps.setString(2, password);
            ps.setInt(3, aid); // Auto-filled from session
            ps.setInt(4, t_id);

            int result = ps.executeUpdate();
            con.close();

            if (result > 0) {
                response.sendRedirect("Admin1.html"); // Redirect to teacher list page
            } else {
                response.getWriter().println("Error updating teacher details.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
