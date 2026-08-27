import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/EditAuditoriumServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2,    // 2MB
                 maxFileSize=1024*1024*10,        // 10MB
                 maxRequestSize=1024*1024*50)     // 50MB
public class EditAuditoriumServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditAuditoriumServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.getWriter().println("Error: ID parameter is missing!");
            return;
        }

        int id = Integer.parseInt(idParam);
        String name = request.getParameter("name");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        Part imagePart = request.getPart("image");

        String imagePath = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = imagePart.getSubmittedFileName();
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            imagePath = "uploads/" + fileName;
            imagePart.write(uploadPath + File.separator + fileName);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");

            if (imagePath != null) {
                PreparedStatement ps = con.prepareStatement("UPDATE auditorium SET name=?, capacity=?, image=? WHERE id=?");
                ps.setString(1, name);
                ps.setInt(2, capacity);
                ps.setString(3, imagePath);
                ps.setInt(4, id);
                ps.executeUpdate();
            } else {
                PreparedStatement ps = con.prepareStatement("UPDATE auditorium SET name=?, capacity=? WHERE id=?");
                ps.setString(1, name);
                ps.setInt(2, capacity);
                ps.setInt(3, id);
                ps.executeUpdate();
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("Admin1.html");
    }
}
