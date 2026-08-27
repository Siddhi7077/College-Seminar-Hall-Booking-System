

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.Teacher;



/**
 * Servlet implementation class ViewTeacherServlet
 */
@WebServlet("/ViewTeacherServlet")
public class ViewTeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewTeacherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		ArrayList<Teacher> teacherList = new ArrayList<>();

		 try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36", "ty36", "sid3232");
	            Statement stmt = con.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM teacher");
               if(con!=null) {
            	   System.out.println("Contioned Succesful");
               }
	            if(rs==null) {
	            	System.out.println("Empty");
	            }
	            while (rs.next()) {
	                Teacher teacher = new Teacher(rs.getInt("id"), rs.getString("user_name"), rs.getString("password"));
	                teacherList.add(teacher);
	            }
	            con.close();
	           
	          
	        }  catch (ClassNotFoundException e) {
	            e.printStackTrace();
	            System.out.println("MySQL driver not found.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("Failed to connect to the database.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        request.setAttribute("teacherList", teacherList);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("admin_dashboard.jsp");
	        dispatcher.forward(request, response);
	    

      //  request.setAttribute("teacherList", teacherList);
    //    System.out.println("Teacher list size "+teacherList.size());
     //   RequestDispatcher dispatcher = request.getRequestDispatcher("admin_dashboard.jsp");
    //    dispatcher.forward(request, response);
	//	doGet(request, response);
	}

}
