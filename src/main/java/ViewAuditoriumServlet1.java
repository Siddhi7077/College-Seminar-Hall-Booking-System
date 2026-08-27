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

@WebServlet("/ViewAuditoriumServlet")
public class ViewAuditoriumServlet1 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>View Auditorium List</title>");
        out.println("<script>");
        out.println("function confirmDelete(id) {" +
                    "if (confirm('Do you really want to delete this record?')) {" +
                    "window.location.href = 'DeleteAuditoriumServlet?id=' + id;" +
                    "}" +
                    "}" );
        out.println("</script>");
        out.println("</head><body>");
        out.println("<h2>Auditorium List</h2>");
        out.println("<table border='1'><thead>");
        out.println("<tr><th>ID</th><th>Auditorium Name</th><th>Capacity</th><th>Image</th><th>Edit</th><th>Delete</th></tr>");
        out.println("</thead><tbody>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM auditorium");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int capacity = rs.getInt("capacity");
                String image = rs.getString("image");
                
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + capacity + "</td>");
                out.println("<td><img src='" + image + "' width='100' height='100'></td>");
                out.println("<td><a href='Edit_Auditorium.jsp?id=" + id + "'>Edit</a></td>");
                out.println("<td><a href='javascript:void(0);' onclick='confirmDelete(" + id + ")' style='color:red;'>Delete</a></td>");
                out.println("</tr>");
            }
            
            con.close();
        } catch (Exception e) {
            out.println("<tr><td colspan='6'>Error: " + e.getMessage() + "</td></tr>");
            e.printStackTrace();
        }
        
        out.println("</tbody></table>");
        out.println("</body></html>");
    }
}
