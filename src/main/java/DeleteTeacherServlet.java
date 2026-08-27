import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteTeacherServlet")
public class DeleteTeacherServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int t_id = Integer.parseInt(request.getParameter("t_id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");
            PreparedStatement ps = con.prepareStatement("DELETE FROM teacher WHERE t_id=?");
            ps.setInt(1, t_id);
            int result = ps.executeUpdate();
            con.close();

            if (result > 0) {
               response.sendRedirect("Admin.html"); // Redirect after deletion
            	//response.sendRedirect("http://localhost:8082/Project/View_Teacher.jsp");
            } else {
                response.getWriter().println("Error deleting teacher.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
