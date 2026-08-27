

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionServlet
*/
@WebServlet("/SessionServlet")
public class SessionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            String email = (String) session.getAttribute("username");
            System.out.println(email);
            // Remove @gmail.com if it exists
            String username = (email != null) ? email.replace("@gmail.com", "") : "Guest";
            
            response.getWriter().write(username);
        } else {
            response.getWriter().write("Guest");
        }
    }
}
