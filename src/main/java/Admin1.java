

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Admin1
 */
@WebServlet("/Admin1")
public class Admin1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin1() {
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
		  String username = request.getParameter("teacherUsername");
	        String password = request.getParameter("teacherPassword");
		
		//ResultSet rs;
			try {
				Connection c;
				Statement s;
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("Done");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/tydb36","ty36","sid3232");
				System.out.println("Done");
			//	s=con.createStatement();
				PreparedStatement ps=con.prepareStatement("insert into teacher(user_name,password) values(?,?)");
				//int r=s.executeUpdate("insert into teacher(user_name,password) values('username','password')");
				ps.setString(1,username);
				ps.setString(2,password);
				int i=ps.executeUpdate();
				if(i>0) {
				out.println("Teacher Added");
				}
			}
			catch(Exception e) {
				System.out.println(e);
				
			}	
		doGet(request, response);

}
}
