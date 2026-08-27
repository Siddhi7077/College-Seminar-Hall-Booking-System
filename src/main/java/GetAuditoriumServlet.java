import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetAuditoriumServlet")
public class GetAuditoriumServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/tydb36";
    private static final String DB_USER = "ty36";
    private static final String DB_PASSWORD = "sid3232";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL driver
            System.out.println("Database Driver Loaded");

            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database Connected");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM auditorium");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                out.println("<option value='" + id + "'>" + name + "</option>");
            }

            con.close();
            System.out.println("Database Connection Closed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
